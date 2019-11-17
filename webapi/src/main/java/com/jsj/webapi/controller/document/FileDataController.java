package com.jsj.webapi.controller.document;

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.webapi.config.AppWeb;
import com.jsj.webapi.config.ServerConfig;
import com.jsj.webapi.dataobject.document.FileData;
import com.jsj.webapi.dto.document.FileDataDTO;
import com.jsj.webapi.service.document.FileDataService;
import com.jsj.webapi.utils.FileUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Slf4j
@RestController
@Api(tags = {"2文档管理"}, description = "文档管理")
@RequestMapping(value = "/web/document")
public class FileDataController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private FileDataService fileDataService;

    @Autowired
    private AppWeb appWeb;


    /**
     * 接口说明：文档列表
     */
    @ApiOperation(value = "文档列表",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileKind", value = "文档类别",  paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fileTitle", value = "文档标题",  paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注",  paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/fileData/list")
    public HttpResult List(String fileKind,String fileTitle,String remark) throws Exception
    {
        FileDataDTO dto1=new FileDataDTO();
        dto1.setFileTitle(fileTitle);
        dto1.setRemark(remark);
        dto1.setFileKind(fileKind);

        Page p1=this.fileDataService.getSearch(dto1,1,500);
        for (Object obj1:p1.getContent())
        {
            Map<String,Object> map1=( Map<String,Object>)obj1;
            map1.put("filedir",  map1.get("filedir").toString());
        }
        return HttpResultUtil.success(p1);
    }

    /**
     * 说明：info明细
     * @param id
     * @return
     */
    @ApiOperation(value="数据明细",notes="")
    @RequestMapping(value = "/fileData/detail",method = {RequestMethod.POST})
    public HttpResult detail(@ApiParam("数据id") @RequestParam(value = "id")  int id)
    {
        FileData d1=this.fileDataService.findOne(id);
        if(d1!=null)
        {
            FileData dto=new FileData();
            BeanUtils.copyProperties(d1,dto);
            return HttpResultUtil.success(dto);
        }
        else
        {
            return HttpResultUtil.error(60,"得到数据明细失败！");
        }
    }


    /**
     * 接口说明：增加一个信息数据
     * @return
     */
    @ApiOperation(value = "新增文档数据",notes = "")
    @PostMapping(value = "/fileData/create")
    public HttpResult create(
            @ApiParam("序号") @RequestParam(value = "num", required = false)  Integer num ,
            @ApiParam("文档类别") @RequestParam(value = "fileKind", required = false) String  fileKind,
            @ApiParam("文件标题") @RequestParam(value = "fileTitle", required = false) String  fileTitle,
            @ApiParam("文件名称") @RequestParam(value = "fileName", required = false) String  fileName,
            @ApiParam("关键字") @RequestParam(value = "filekeys", required = false) String  filekeys,
            @ApiParam("备注") @RequestParam(value = "remark", required = false) String  remark,
            @ApiParam("文档文件") @RequestParam(value = "file", required = false) MultipartFile file
    ) throws  Exception
    {
        String filedir="";
        String fileName1="";
        if(file!=null) {
            filedir= FileUtils.UploadFile(file, fileKind);
            fileName1=file.getOriginalFilename();
        }
        FileData data1=new FileData();
        data1.setDefault();
        data1.setNum(num);
        data1.setFileKind(fileKind);
        data1.setFileTitle(fileTitle);
        data1.setFiledir(filedir);
        data1.setFilekeys(filekeys);
        data1.setRemark(remark);
        if(MyStringUtil.isEmpty(fileName)) {
            data1.setFileName(fileName1);
        }
        else {
            data1.setFileName(fileName);
        }
        this.fileDataService.save(data1);

        Map<String, String> map = new HashMap<>();
        map.put("ID", data1.getId()+"");
        return  HttpResultUtil.success(map);
    }

    /**
     * 接口说明：更新数据
     * @return
     */
    @ApiOperation(value = "更新文档数据",notes = "")
    @PostMapping(value = "/fileData/update")
    public HttpResult update(
            @ApiParam("数据ID") @RequestParam(value = "id", required = true)  Integer id ,
            @ApiParam("序号") @RequestParam(value = "num", required = false)  Integer num ,
            @ApiParam("文档类别") @RequestParam(value = "fileKind", required = false) String  fileKind,
            @ApiParam("文件标题") @RequestParam(value = "fileTitle", required = false) String  fileTitle,
            @ApiParam("文件名称") @RequestParam(value = "fileName", required = false) String  fileName,
            @ApiParam("关键字") @RequestParam(value = "filekeys", required = false) String  filekeys,
            @ApiParam("备注") @RequestParam(value = "remark", required = false) String  remark,
            @ApiParam("文档文件") @RequestParam(value = "file", required = false) MultipartFile file
    ) throws Exception
    {
        String filedir="";
        String fileName1="";
        if(file!=null) {
            filedir= FileUtils.UploadFile(file, fileKind);
            fileName1=file.getOriginalFilename();
        }
        FileData data1=this.fileDataService.findOne(id);
        if(data1==null)
        {
            data1=new FileData();
        }
        data1.setDefault();
        data1.setNum(num);
        data1.setFileKind(fileKind);
        data1.setFileTitle(fileTitle);
        if(MyStringUtil.isNotEmpty(filedir)) {
            data1.setFiledir(filedir);
        }
        data1.setFilekeys(filekeys);
        data1.setRemark(remark);
        if(MyStringUtil.isEmpty(fileName)) {
            data1.setFileName(fileName1);
        }
        else {
            data1.setFileName(fileName);
        }
        this.fileDataService.save(data1);

        Map<String, String> map = new HashMap<>();
        map.put("ID", data1.getId()+"");
        return  HttpResultUtil.success(map);
    }

    /**
     * 说明：删除数据
     * @param ids
     * @return
     */
    @ApiOperation(value="删除数据",notes="")
    @RequestMapping(value = "/fileData/delete",method = {RequestMethod.POST})
    public HttpResult delete(@ApiParam("数据id列表") @RequestParam(value = "ids")  String ids)
    {
        int id1= this.fileDataService.deletMulDevice(ids);
        if(id1>0)
        {
            return HttpResultUtil.success();
        }
        else
        {
            return HttpResultUtil.error(40,"删除数据操作失败！");
        }
    }
}
