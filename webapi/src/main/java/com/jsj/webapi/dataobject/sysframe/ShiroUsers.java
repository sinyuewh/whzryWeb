package com.jsj.webapi.dataobject.sysframe;/**
 * Created by jinshouji on 2018/8/25.
 */

import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;

/**
 * @author ：jinshouji
 * @create ：2018-08-25 14:21
 * @remark：ShiroUsers 保存用户的登录信息（不保存到数据库）
 **/

@Data
public class ShiroUsers implements Serializable {

    private static final long serialVersionUID = 9108429500288305705L;

    private String  userLoginId;         //用户登录ID（用户的工号）
    private int userid;                  //用户的id
    private String userName;            //真实姓名
    private int userType;               //用户类型(0--  1--- 2---）
    private String loginIp;              //登陆用户的IP

    private int paentId;                //用户所属的组织id
    private String roleName;            //用户的权限角色
    private String[] permissionsId;     //用户的权限id

    /**
     * 得到当前的登陆用户的信息
     * @return
     */
    public static ShiroUsers getCurrentUser()
    {
        Subject currentUser = SecurityUtils.getSubject();
        Object object= currentUser.getPrincipal();
        if(object instanceof ShiroUsers)
        {
            return (ShiroUsers)object;
        }
        else
        {
            return null;
        }
    }
}
