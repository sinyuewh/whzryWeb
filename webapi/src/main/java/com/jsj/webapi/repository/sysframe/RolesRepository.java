package com.jsj.webapi.repository.sysframe;/**
 * Created by jinshouji on 2018/8/18.
 */

import com.jsj.common.repository.BaseRepository;
import com.jsj.webapi.dataobject.sysframe.Roles;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：jinshouji
 * @create ：2018-08-18 13:32
 **/

public interface RolesRepository extends BaseRepository<Roles, Integer> {

    //删除多个选中的角色数据
    @Modifying
    @Transactional
    @Query("DELETE FROM  Roles r WHERE r.id in (?1)")
    public void deleteRolesInBatchByIdIn(List<Integer> ids);

    //根据角色的ID返回多条数据
    public List<Roles> findByIdIn(List<Integer> ids);
}
