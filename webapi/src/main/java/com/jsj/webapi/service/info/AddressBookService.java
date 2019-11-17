package com.jsj.webapi.service.info;

import com.jsj.common.service.BaseService;
import com.jsj.webapi.dataobject.info.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Service
@Slf4j
public class AddressBookService extends BaseService<AddressBook, Integer> {
    //用来记录日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
