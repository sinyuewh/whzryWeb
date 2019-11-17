package com.jsj.webapi.dataobject.sysframe;

import com.jsj.webapi.dataobject.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jinshouji on 2018/4/30.
 */

/**
 * @author ：jinshouji
 * @create ：2018-04-25 18:03
 * @remark:  框架系统表--用户表
 **/

@Data
@Entity
@Table
public class Users extends AbstractEntity implements Serializable
{
    private static final long serialVersionUID = 7168463797339608366L;

    @Column(length=50,unique=true,nullable=false)
    private String userLoginId;       //用户登录ID（用户的工号）

    @Column(length =50,nullable=false)
    private String password;        //登陆密码

    @Column(length = 50,nullable = false)
    private String userName;        //真实姓名

    private String mobile;          //手机号
    private int paentId=0;         //用户所属的组织id

    /*------------------------------------------------------*/
    private Date lastLogin;         //最近登录
    private int loginCount=0;         //登录次数
    private int userType=0;          //用户类型(0--  1---  2---）
    private int userClass=0;        //用户级别 0---0  1---  2---  3--
    private String photo;           //相片文件

    /*------------------------------------------------------*/
    private String roleName;             //用户的角色Name
    private int sex=0;                  //用户性别 0--表示男 1--表示女
    private String signaturePicture;    //用户签名图片的相对url
    private String remark;               //用户备注
    @Transient
    private String photoUrl;
    @Transient
    private String signaturePictureUrl;
}
