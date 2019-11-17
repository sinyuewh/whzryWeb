package com.jsj.webapi.repository.sysframe;/**
 * Created by jinshouji on 2018/8/18.
 */

import com.jsj.common.repository.BaseRepository;
import com.jsj.webapi.dataobject.sysframe.RolePermissions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：jinshouji
 * @create ：2018-08-18 13:31
 **/
public interface RolePermissionsRepository extends BaseRepository<RolePermissions, Integer> {

    //根据角色的RoleName 返回多条角色权限数据
    List<RolePermissions> findByRoleName(String roleName);

    @Modifying
    @Transactional
    @Query("delete from RolePermissions s where s.roleName = ?1")
    void deleteBatch(String roleName);


    @Modifying
    @Transactional
    @Query(value = "delete from RolePermissions WHERE roleName in (:roleNames)",nativeQuery = true)
    public void deleteBatchByRoleNameIn(@Param("roleNames") List<String> roleNames);
}
