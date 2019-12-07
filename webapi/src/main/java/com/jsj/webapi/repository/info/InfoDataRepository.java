package com.jsj.webapi.repository.info;/**
 * Created by jinshouji on 2018/8/18.
 */

import com.jsj.common.repository.BaseRepository;
import com.jsj.webapi.dataobject.info.InfoData;

import java.util.List;

/**
 * @author ：jinshouji
 * @create ：2018-08-18 13:28
 **/

public interface InfoDataRepository extends BaseRepository<InfoData, Integer> {

    //根据名称字段得到第一条数据
    public List<InfoData> findAllByStr1AndInfoKind(String Str1, String infoKind);

}
