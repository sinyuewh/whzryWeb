package com.jsj.webapi.service.sysframe;/**
 * Created by jinshouji on 2018/8/27.
 */

import com.jsj.common.service.BaseService;
import com.jsj.common.utils.jpa.SearchField;
import com.jsj.webapi.dataobject.sysframe.Roles;
import com.jsj.webapi.repository.sysframe.RolePermissionsRepository;
import com.jsj.webapi.repository.sysframe.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：jinshouji
 * @create ：2018-08-27 9:45
 **/
@Service
@Slf4j
public class RolesService extends BaseService<Roles,Integer> {
    //用来记录日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolePermissionsRepository rolePermissionsRepository;

    /**
     * 说明：得到角色的列表数据
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Transactional
    public Page getList(
            int pageIndex,int pageSize
    )
    {
        List<SearchField> condition=new ArrayList<SearchField>();

        //设置关联表的查询
        String tableName="roles LEFT JOIN roleuserscountview on roles.roleName=roleuserscountview.rolename";
        this.setTableName(tableName);

        //ID	角色名	用户列表	 描述
        String fs="roles.id,roles.roleName,IFNull(roleuserscountview.userscount,0) userscount,roles.remark" ;

        Page p1= this.getPageListMapData(fs,"id desc",condition,pageIndex,pageSize);
        return p1;
    }


    //成批的删除多个角色的数据
    @Transactional
    public void deleteBatchRoles(List<Integer> ids)
    {
        //根据IDS得到Roles的多条数据
        List<Roles> list1=this.rolesRepository.findByIdIn(ids);

        List<String> roleNameList=new ArrayList<String>();
        for(Roles r1:list1)
        {
            roleNameList.add(r1.getRoleName());
        }

        //删除多个角色数据
        this.rolesRepository.deleteRolesInBatchByIdIn(ids);
        
        //删除多个角色关联的权限数据
        if(roleNameList.size()>0) {
            this.rolePermissionsRepository.deleteBatchByRoleNameIn(roleNameList);
        }
    }
}
