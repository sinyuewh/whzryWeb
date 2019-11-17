package com.jsj.webapi.repository.sysframe;/**
 * Created by jinshouji on 2018/8/18.
 */

import com.jsj.common.repository.BaseRepository;
import com.jsj.webapi.dataobject.sysframe.Users;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ：jinshouji
 * @create ：2018-08-18 13:54
 **/

public interface UsersRepository extends BaseRepository<Users, Integer> {

    //根据用户的userLoginId 返回用户的实体
    Users getUsersByUserLoginId(String userLoginId);

    //利用用户的姓名返回用户的实体
    Users getUsersByUserName(String userName);

    @Query("select s from Users s where s.userLoginId=?1 and s.password=?2 and s.status=0")
    Users login(String userLoginId, String password);

}
