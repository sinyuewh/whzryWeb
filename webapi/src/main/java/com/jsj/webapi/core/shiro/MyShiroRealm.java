package com.jsj.webapi.core.shiro;/**
 * Created by jinshouji on 2018/5/14.
 */

import com.jsj.webapi.config.AppWeb;
import com.jsj.webapi.dataobject.sysframe.ShiroUsers;
import com.jsj.webapi.repository.sysframe.RolePermissionsRepository;
import com.jsj.webapi.service.sysframe.UsersService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ：jinshouji
 * @create ：2018-05-14 11:52
 * @Remark ：Shiro的身份验证的自定义类
 **/
public class MyShiroRealm extends AuthorizingRealm
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //表示当前登录的用户
    private ShiroUsers currentUsers=null;

    @Autowired
    private UsersService usersService;

    @Autowired
    private AppWeb appWeb;

    @Autowired
    private RolePermissionsRepository rolePermissionsRepository;

    /**
     * 认证信息.(身份验证)
     * :
     * Authentication 是用来验证用户身份
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("前台登录认证：CustomShiroRealm.doGetAuthenticationInfo()");
        //1) 从主体传过来的信息获得用户名
        String username=(String)token.getPrincipal();
        String password1= new String(((UsernamePasswordToken)token).getPassword());

        //2）通过用户名到数据库中获取密码（利用数据库的操作）
        //同时支持明文和MD5加密的密码
        String password=getPasswordByUserName(username);
        if(password.equals(password1)) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(this.currentUsers, password, username);
            return simpleAuthenticationInfo;  //返回用户登陆的权限信息（用户名/密码/真实姓名）
        }
        else
        {
            return null;
        }
    }

    /**
     * 此方法调用  hasRole,hasPermission的时候才会进行回调.
     *
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
       /*
        * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
        * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
        * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
        * 缓存过期之后会再次执行。
        */
        //得到用户的登录ID
        ShiroUsers username=(ShiroUsers)principals.getPrimaryPrincipal();
        if(username!=null) {
            /*----这里构建用户的角色和权限------*/
            //构建用户的roles数据
            Set<String> role = new HashSet<String>();

            //添加用户的角色
            role.add(username.getRoleName());

            //构建用户的权限数据
            Set<String> permission = new HashSet<String>();
            for(String m :username.getPermissionsId())
            {
                permission.add(m);
            }

            //返回权限的数据
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.setRoles(role);
            simpleAuthorizationInfo.setStringPermissions(permission);

            return simpleAuthorizationInfo;
        }
        else
        {
            return null;
        }
    }

    //根据用户名返回用户保存在数据库中的登录密码
    private String getPasswordByUserName(String userLoginID)
    {
        return "123456";
    }
}
