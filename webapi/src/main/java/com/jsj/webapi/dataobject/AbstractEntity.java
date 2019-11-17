package com.jsj.webapi.dataobject;/**
 * Created by jinshouji on 2018/8/18.
 */

import com.jsj.common.utils.KeyUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：jinshouji
 * @create ：2018-08-18 11:23
 * @remar : 通用的实体对象
 **/
@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private int id;                 //数据id

    private int weight;             //数据的权重（用于排序）
    private int status;             //数据的状态（0 为默认值）

    @Column(length=20)
    private String createPerson;    //创建用户

    private Date createTime;        //创建时间

    @Column(length=20)
    private String modifyPerson;    //修改用户
    private Date modifyTime;        //修改时间

    @Column(length=50,unique = true)
    private String guid;            //数据的guid

    //构造函数
    public AbstractEntity()
    {
        this.setDefault();
    }

    /**
     * 设置默认值（适合新增数据）
     */
    public void setDefault()
    {
        this.weight=0;
        this.status=0;

        this.createTime=new Date();
        this.modifyTime=new Date();

        this.guid= KeyUtil.get32UUID();
    }

}
