package com.jsj.webapi.repository.sysframe;/**
 * Created by jinshouji on 2018/8/18.
 */

import com.jsj.common.repository.BaseRepository;
import com.jsj.webapi.dataobject.sysframe.Orgs;

/**
 * @author ：jinshouji
 * @create ：2018-08-18 13:28
 **/

public interface OrgsRepository extends BaseRepository<Orgs, Integer> {

    //利用项目的名称，返回项目的资料
    Orgs getByOrgName(String orgName);
}
