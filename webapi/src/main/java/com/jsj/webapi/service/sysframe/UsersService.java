package com.jsj.webapi.service.sysframe;

import com.jsj.common.service.BaseService;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.webapi.dataobject.sysframe.Users;
import com.jsj.webapi.repository.sysframe.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Service
@Slf4j
public class UsersService extends BaseService<Users, Integer>
{
    //用来记录日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsersRepository usersRepository;

    /**
     * 根据用户登陆名得到用户的实体
     * @param userLoginId
     * @return
     */
    @Transactional
    public Users getUsersByUserLoginId(String userLoginId) {
        if (MyStringUtil.isNotEmpty(userLoginId)) {
            return this.usersRepository.getUsersByUserLoginId(userLoginId);
        } else {
            return null;
        }
    }
}
