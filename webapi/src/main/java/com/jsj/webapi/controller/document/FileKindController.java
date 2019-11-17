package com.jsj.webapi.controller.document;

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.webapi.config.AppWeb;
import com.jsj.webapi.config.ServerConfig;
import com.jsj.webapi.dataobject.document.FileKind;
import com.jsj.webapi.dto.document.FileKindDTO;
import com.jsj.webapi.enums.ErrorEnum;
import com.jsj.webapi.exception.MyException;
import com.jsj.webapi.service.document.FileKindService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Slf4j
@RestController
@Api(tags = {"2文档管理"}, description = "文档管理")
@RequestMapping(value = "/web/document")
public class FileKindController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private FileKindService fileKindService;

    @Autowired
    private AppWeb appWeb;

    /**
     * 接口说明：得到所有的文档类别
     * @return
     */
    @ApiOperation(value = "得到文档的所有类别",notes = "")
    @PostMapping(value = "/fileKind/getAll")
    public HttpResult getAll() throws Exception
    {
        FileKindDTO dto1=new FileKindDTO();
        Page p1=this.fileKindService.getSearch(dto1,1,500);
        return HttpResultUtil.success(p1);
    }

    /**
     * 接口说明：文档类别列表
     */
    @ApiOperation(value = "文档类别列表",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileKind", value = "类别名称",  paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注",  paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/fileKind/list")
    public HttpResult List(String fileKind,String remark) throws Exception
    {
        FileKindDTO dto1=new FileKindDTO();
        dto1.setFileKind(fileKind);
        dto1.setRemark(remark);
        Page p1=this.fileKindService.getSearch(dto1,1,500);
        return HttpResultUtil.success(p1);
    }

    /**
     * 说明：info明细
     * @param id
     * @return
     */
    @ApiOperation(value="数据明细",notes="")
    @RequestMapping(value = "/fileKind/detail",method = {RequestMethod.POST})
    public HttpResult detail(@ApiParam("数据id") @RequestParam(value = "id")  int id)
    {
        FileKind d1=this.fileKindService.findOne(id);
        if(d1!=null)
        {
            FileKindDTO dto=new FileKindDTO();
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
    @ApiOperation(value = "新增文档类别数据",notes = "")
    @PostMapping(value = "/fileKind/create")
    public HttpResult create(@ApiParam("文档类别json") @RequestBody @Valid FileKindDTO para,
                             BindingResult bindingResult)
    {
        //验证数据的是否正确
        if (bindingResult.hasErrors()) {
            log.error("【信息数据】参数不正确, orderForm={}", para);
            throw new MyException(ErrorEnum.Lift_Error.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //创建对象和赋值
        FileKind target=new FileKind();
        BeanUtils.copyProperties(para,target);
        target.setDefault();

        //保存数据
        target=this.fileKindService.save(target);
        Map<String, String> map = new HashMap<>();
        map.put("ID", target.getId()+"");
        return  HttpResultUtil.success(map);
    }

    /**
     * 接口说明：更新数据
     * @return
     */
    @ApiOperation(value = "更新文档类别数据",notes = "")
    @PostMapping(value = "/fileKind/update")
    public HttpResult update(@ApiParam("文档类别json") @RequestBody @Valid FileKindDTO para,
                             BindingResult bindingResult)
    {
        //验证数据的是否正确
        if (bindingResult.hasErrors()) {
            log.error("【信息数据】参数不正确, orderForm={}", para);
            throw new MyException(ErrorEnum.Lift_Error.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //创建对象和赋值
        FileKind target=this.fileKindService.findOne(para.getId());
        if(target==null)
        {
            target=new FileKind();
            BeanUtils.copyProperties(para,target);
            target.setDefault();
        }
        else {
            BeanUtils.copyProperties(para,target);
        }
        //保存数据
        target=this.fileKindService.save(target);
        Map<String, String> map = new HashMap<>();
        map.put("ID", target.getId()+"");
        return  HttpResultUtil.success(map);
    }

    /**
     * 说明：删除数据
     * @param ids
     * @return
     */
    @ApiOperation(value="删除数据",notes="")
    @RequestMapping(value = "/fileKind/delete",method = {RequestMethod.POST})
    public HttpResult delete(@ApiParam("数据id列表") @RequestParam(value = "ids")  String ids)
    {
        int id1= this.fileKindService.deletMulDevice(ids);
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
