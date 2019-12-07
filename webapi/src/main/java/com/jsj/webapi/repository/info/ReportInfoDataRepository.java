package com.jsj.webapi.repository.info;/**
 * Created by jinshouji on 2018/8/18.
 */

import com.jsj.common.repository.BaseRepository;
import com.jsj.webapi.dataobject.info.ReportInfoData;

/**
 * @author ：jinshouji
 * @create ：2018-08-18 13:28
 **/

public interface ReportInfoDataRepository extends BaseRepository<ReportInfoData, Integer> {

    public ReportInfoData findFirstByParent(Integer parent);

}
