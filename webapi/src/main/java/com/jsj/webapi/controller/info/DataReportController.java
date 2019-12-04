package com.jsj.webapi.controller.info;/**
 * Created by jinshouji on 2018/10/19.
 */

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.webapi.dataobject.log.OperateLog;
import com.jsj.webapi.dataobject.sysframe.Orgs;
import com.jsj.webapi.dto.sysframe.OrgsDTO;
import com.jsj.webapi.enums.ErrorEnum;
import com.jsj.webapi.exception.MyException;
import com.jsj.webapi.service.log.OperateLogService;
import com.jsj.webapi.service.sysframe.OrgsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：jinshouji
 * 说明：数据上报接口
 **/

@Slf4j
@RestController
@Api(tags = {"6系统管理"}, description = "数据上报")
@RequestMapping(value = "/web/dataReport")
public class DataReportController {

    //用来记录日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrgsService orgsService;

    @Autowired
    private OperateLogService operateLogService;

    @ApiOperation(value = "生成上报文件（明文）",notes = "")
    @PostMapping(value = "/createReportFile")
    public HttpResult create(String ids)
    {
        return null;
    }
}
