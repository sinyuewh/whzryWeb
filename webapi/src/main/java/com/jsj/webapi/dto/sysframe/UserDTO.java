package com.jsj.webapi.dto.sysframe;/**
 * Created by jinshouji on 2018/10/22.
 */

import lombok.Data;

import java.util.Date;

/**
 * @author ：jinshouji
 * @create ：2018-10-22 11:18
 * @remark ：用户数据DTO
 **/
@Data
public class UserDTO {

    private  int id;                           //数据的ID

    private String userLoginId;       //用户登录ID（用户的工号）

    private String userName;        //真实姓名

    private String mobile;          //手机号
    private int paentId=0;         //用户所属的组织id

    private Date lastLogin;         //最近登录
    private int loginCount=0;         //登录次数
    private int userType=0;          //用户类型(0--  1---  2---）
    private int userClass=0;        //用户级别 0---0  1---  2---  3--
    private String photo;           //相片文件

    private String roleName;             //用户的角色Name
    private int sex=0;                  //用户性别 0--表示男 1--表示女
    private String signaturePicture;    //用户签名图片的相对url
    private String remark;               //用户备注

}
