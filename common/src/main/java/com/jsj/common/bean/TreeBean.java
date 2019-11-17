package com.jsj.common.bean;/**
 * Created by jinshouji on 2018/4/27.
 */

import lombok.Data;

import java.util.List;

/**
 * @author ：jinshouji
 * @create ：2018-04-27 10:14
 * @Remark : 表示树结构
 **/

@Data
public class TreeBean {
    private String id;                               //节点id
    private String text;                             //节点文本
    private String pid;                              //父节点id
    private String state = "open";                  //节点的状态为open 或close
    private boolean checked = false;               //节点是否选中
    private List<TreeBean> children;                //节点的子节点

    //无参构造函数
    public TreeBean() {
    }

    //构造函数1
    public TreeBean(String id, String text, String pid) {
        this.id = id;
        this.text = text;
        this.pid = pid;
    }

    //构造函数2
    public TreeBean(String id, String text) {
        this.id = id;
        this.text = text;
    }
}

