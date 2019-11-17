package com.jsj.webapi.controller.Login;/**
 * Created by jinshouji on 2018/10/19.
 */

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.webapi.dataobject.log.OperateLog;
import com.jsj.webapi.dataobject.sysframe.Users;
import com.jsj.webapi.dto.sysframe.UserDTO;
import com.jsj.webapi.service.log.OperateLogService;
import com.jsj.webapi.service.sysframe.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ：jinshouji
 * 说明：用户登陆和注销
 **/

@Slf4j
@RestController
@Api(tags = {"0登陆和注销"}, description = "登陆和注销")
@RequestMapping(value = "/web")
public class LoginController {

    //用来记录日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private  UsersService usersService;

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 接口说明：Web用户登录（包括平台用户和业主用户）
     *
     * @param userName      用户名
     * @param password      登录密码
     * @return
     */
    @ApiOperation(value = "用户登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "password", value = "登陆密码", required = true, paramType = "query", dataType = "String"),
    })
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public HttpResult Login( String userName, String password) throws  Exception
    {
        if(MyStringUtil.isNotEmpty(userName) && MyStringUtil.isNotEmpty(password)) {
            Users users = usersService.getUsersByUserLoginId(userName);
            if(users!=null && (users.getPassword().equals(password) || users.getPassword().equals(MyStringUtil.md5(password))))
            {
                UserDTO userDTO=new UserDTO();
                BeanUtils.copyProperties(users,userDTO);
                //插入登录日志
                OperateLog log1=new OperateLog();
                log1.setDefault();
                log1.setUserName(userName);
                log1.setOperatTime(new Date());
                log1.setOperatContent("登录");
                this.operateLogService.save(log1);

                return HttpResultUtil.success(userDTO.getUserLoginId());
            }
            else {
                return HttpResultUtil.error(901,"用户名或密码输入错误！");
            }
        }
        return  HttpResultUtil.error(900,"用户名或密码不能为空！");
    }

    /**
     * 接口说明：获取用户的登陆信息，如果没有登陆，则获取null信息
     * @return
     */
    @ApiOperation(value = "当前用户登陆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户ID", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value="/userinfo",method = {RequestMethod.POST})
    public HttpResult getCurrentUserLoginInfo(String userName)
    {
        if(MyStringUtil.isNotEmpty(userName)) {
            Users users = usersService.getUsersByUserLoginId(userName);
            if(users!=null)
            {
                UserDTO userDTO=new UserDTO();
                BeanUtils.copyProperties(users,userDTO);
                return HttpResultUtil.success(userDTO);
            }
        }
        return  HttpResultUtil.error(900,"用户不存在！");
    }


    /**
     * 接口说明：用户退出登陆
     * @return
     */
    @ApiOperation(value="用户注销退出",notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户ID", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value="/signout",method = {RequestMethod.POST, RequestMethod.GET})
    public HttpResult signout( String userName)
    {
        return HttpResultUtil.success();
    }


    /**
     * 接口说明：用户退出登陆
     * @return
     */
    @ApiOperation(value="修改登陆密码",notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "oldpass", value = "用户老密码", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pass1", value = "登陆新密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pass2", value = "重复新密码", required = true, paramType = "query", dataType = "String"),
    })
    @RequestMapping(value="/modifyPassword",method = {RequestMethod.POST})
    public HttpResult modifyPassword( String userName,String oldpass, String pass1, String pass2 )
    {
        Boolean succ=false;
        if(MyStringUtil.isEmpty(userName))
        {
            return HttpResultUtil.error(1000,"错误：当前用户没有登录！");
        }
        //1--验证新密码长度
        if(pass1.length()<6)
        {
            return HttpResultUtil.error(1000,"错误：密码的长度不得低于6位！");
        }
        //2--验证密码的两次输入的值
        if(!pass1.equals(pass2))
        {
            return HttpResultUtil.error(1001,"错误：密码两次输入的值不一样！");
        }
        //4--验证用户是否存在
        Users u1=this.usersService.getUsersByUserLoginId(userName);
        if(u1==null||u1.getStatus()!=0)
        {
            return HttpResultUtil.error(1003,"错误：当前的用户名不存在或已注销！");
        }
        try {
            String oldpass1 = MyStringUtil.md5(oldpass);
            String truepass1 = u1.getPassword();
            if(truepass1.equals(oldpass) || truepass1.equals(oldpass1))
            {
                //更新数据库中的老密码
                u1.setPassword(MyStringUtil.md5(pass1));
                this.usersService.save(u1);
                succ=true;
            }
            else
            {
                return HttpResultUtil.error(1005,"错误：老密码输入不正确，请重新输入！");
            }
        }
        catch(Exception err)
        {
            logger.error(err.getMessage());
            return HttpResultUtil.error(1004,"错误：系统错误，请咨询管理员！");
        }

        if(succ) {
            return HttpResultUtil.success();
        }
        else
        {
            return HttpResultUtil.error(1004,"错误：系统错误，请咨询管理员！");
        }
    }
}
