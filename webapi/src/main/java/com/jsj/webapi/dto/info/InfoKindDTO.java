package com.jsj.webapi.dto.info;/**
 * Created by jinshouji on 2018/10/22.
 */

import lombok.Data;

/**
 * @author ：jinshouji
 * @create ：2018-10-22 11:18
 * @remark ：信息数据DTO
 **/
@Data
public class InfoKindDTO {
    private String  text;               //数据标题
    private String  excel;              //excel模板
    private String  word;               //word模板

    public InfoKindDTO(){;}
    public InfoKindDTO(String text,String excel,String word)
    {
        this.text=text;
        this.excel=excel;
        this.word=word;
    }
}
