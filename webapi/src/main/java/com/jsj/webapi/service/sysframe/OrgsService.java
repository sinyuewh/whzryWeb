package com.jsj.webapi.service.sysframe;

import com.jsj.common.service.BaseService;
import com.jsj.webapi.dataobject.sysframe.Orgs;
import com.jsj.webapi.repository.sysframe.OrgsRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Service
@Slf4j
public class OrgsService  extends BaseService<Orgs, Integer> {
    //用来记录日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrgsRepository orgsRepository;

}
