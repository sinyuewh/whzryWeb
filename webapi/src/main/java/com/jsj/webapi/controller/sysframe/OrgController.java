package com.jsj.webapi.controller.sysframe;/**
 * Created by jinshouji on 2018/10/19.
 */

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.webapi.dataobject.info.InfoData;
import com.jsj.webapi.dataobject.log.OperateLog;
import com.jsj.webapi.dataobject.sysframe.Orgs;
import com.jsj.webapi.dataobject.sysframe.Users;
import com.jsj.webapi.dto.info.InfoDTO;
import com.jsj.webapi.dto.sysframe.OrgsDTO;
import com.jsj.webapi.dto.sysframe.UserDTO;
import com.jsj.webapi.enums.ErrorEnum;
import com.jsj.webapi.exception.MyException;
import com.jsj.webapi.service.log.OperateLogService;
import com.jsj.webapi.service.sysframe.OrgsService;
import com.jsj.webapi.service.sysframe.UsersService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：jinshouji
 * 说明：系统管理
 **/

@Slf4j
@RestController
@Api(tags = {"6系统管理"}, description = "系统管理")
@RequestMapping(value = "/web/orgs")
public class OrgController {

    //用来记录日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrgsService orgsService;

    @Autowired
    private OperateLogService operateLogService;

    @ApiOperation(value = "新增数据",notes = "")
    @PostMapping(value = "/create")
    public HttpResult create(@ApiParam("信息对象json") @RequestBody @Valid OrgsDTO para, BindingResult bindingResult)
    {
        //验证数据的是否正确
        if (bindingResult.hasErrors()) {
            log.error("【信息数据】参数不正确, orderForm={}", para);
            throw new MyException(ErrorEnum.Lift_Error.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //创建对象和赋值
        Orgs target=new Orgs();
        BeanUtils.copyProperties(para,target);
        target.setDefault();

        //保存数据
        target=this.orgsService.save(target);
        Map<String, String> map = new HashMap<>();
        map.put("infoID", target.getId()+"");

        //插入操作日志
        OperateLog log1 = new OperateLog();
        log1.setLogType(1);
        log1.setDefault();
        log1.setUserName("admin");
        log1.setOperatTime(new Date());
        log1.setOperatContent("新增【上报单位】："+para.getOrgName()+",id："+para.getId());
        this.operateLogService.save(log1);

        return  HttpResultUtil.success(map);
    }
}
