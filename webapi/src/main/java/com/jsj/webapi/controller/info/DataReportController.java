package com.jsj.webapi.controller.info;/**
 * Created by jinshouji on 2018/10/19.
 */

import com.jsj.common.bean.HttpResult;
import com.jsj.webapi.service.info.InfoDataService;
import com.jsj.webapi.service.log.OperateLogService;
import com.jsj.webapi.service.sysframe.OrgsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private InfoDataService infoDataService;

    @Autowired
    private OperateLogService operateLogService;

    @ApiOperation(value = "根据待办数据ID生成上报文件",notes = "")
    @PostMapping(value = "/createReportFile")
    public HttpResult create(String ids)
    {
        return null;
    }

    @ApiOperation(value = "一键上报生成上报文件",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerTime", value = "登记时间",  paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/onClickToReport")
    public HttpResult onClickToReport(String registerTime)
    {
        return null;
    }

}
