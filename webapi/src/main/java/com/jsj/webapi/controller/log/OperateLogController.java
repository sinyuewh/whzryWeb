package com.jsj.webapi.controller.log;

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.common.utils.KeyUtil;
import com.jsj.webapi.config.AppWeb;
import com.jsj.webapi.config.ServerConfig;
import com.jsj.webapi.dataobject.log.OperateLog;
import com.jsj.webapi.dto.info.InfoDTO;
import com.jsj.webapi.dto.log.OperateLogDTO;
import com.jsj.webapi.service.log.OperateLogService;
import com.jsj.webapi.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Slf4j
@RestController
@Api(tags = {"4系统管理"}, description = "日志管理")
@RequestMapping(value = "/web/operateLog")
public class OperateLogController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private AppWeb appWeb;

       /**
     * 接口说明：日志数据列表接口
     * @param para
     * @return
     */
    @ApiOperation(value = "列表数据",notes = "")
    @PostMapping(value = "/list")
    public HttpResult List(@ApiParam("查询条件") OperateLogDTO para) throws Exception
    {
        Page p1=this.operateLogService.getSearch(para,1,appWeb.getPageSize());
        return HttpResultUtil.success(p1);
    }


    /**
     * 说明：info明细
     * @param id
     * @return
     */
    @ApiOperation(value="数据明细",notes="")
    @RequestMapping(value = "/detail",method = {RequestMethod.POST})
    public HttpResult detail(@ApiParam("数据id") @RequestParam(value = "id")  int id)
    {
        OperateLog d1=this.operateLogService.findOne(id);
        if(d1!=null)
        {
            InfoDTO dto=new InfoDTO();
            BeanUtils.copyProperties(d1,dto);
            return HttpResultUtil.success(dto);
        }
        else
        {
            return HttpResultUtil.error(60,"得到数据明细失败！");
        }
    }

    /**
     * 说明：删除数据
     * @param ids
     * @return
     */
    @ApiOperation(value="删除数据",notes="")
    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    public HttpResult delete(@ApiParam("数据id列表") @RequestParam(value = "ids")  String ids)
    {
        int id1= this.operateLogService.deletMulDevice(ids);
        if(id1>0)
        {
            return HttpResultUtil.success();
        }
        else
        {
            return HttpResultUtil.error(40,"删除数据操作失败！");
        }
    }

    @ApiOperation(value="导出Excel",notes="")
    @PostMapping(value = "/export")
    public HttpResult exportToExcel(@ApiParam("查询条件") OperateLogDTO para ) throws Exception
    {
        Page p1=this.operateLogService.getSearch(para,1,10000);
        Integer begRow=1;
        Map<Integer,String> colMapper=new HashedMap();

        if(p1.getContent().size()<=0)
        {
            return HttpResultUtil.error(900,"没有满足条件的数据！");
        }
        else
        {
            String modelFiledir="";
            String filePrex="";
            //定义模板和excel的映射关系
            modelFiledir=FileUtils.getFileRootPath()+appWeb.getTemplates()+ File.separator+"操作日志.xls";
            filePrex="操作日志";
            begRow=1;
            //改
//            for(int i=1;i<=3;i++) colMapper.put(i,"str"+i);
            colMapper.put(1,"userName");
            colMapper.put(2,"operatTime");
            colMapper.put(3,"operatContent");

            File modelFile = new File(modelFiledir);
            if(!modelFile.exists())
            {
                return HttpResultUtil.error(900,"项目导出的模板文件不存在！");
            }

            InputStream is = new FileInputStream(modelFile);
            Workbook wb = new HSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);

            //设置样式和字体
            //写入项目的质检数据
            CellStyle style= wb.createCellStyle();

            style.setAlignment(HorizontalAlignment.CENTER);//水平居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

            // 设置边框
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);

            // 自动换行
            style.setWrapText(true);

            List<Map<String,Object>> data1=p1.getContent();
            int maxRow=begRow;
            for(Map<String,Object> item : data1) {
                //根据条件得到数据，并将数据写入到sheet
                Row row1 = sheet.getRow(maxRow);
                if (row1 == null) {
                   row1 = sheet.createRow(maxRow);
                }
                //设置行高
                row1.setHeight((short) (25*15.625));

                for(int i=0;i<colMapper.keySet().size()+1;i++) {
                    Cell cel1 = row1.getCell(i);
                    if (cel1 == null) {
                        cel1 = row1.createCell(i);
                        cel1.setCellStyle(style);
                    }
                    if(i==0)
                    {
                        cel1.setCellValue(maxRow+"");
                    }
                    else {
                        String value1 = "";
                        if (item.get(colMapper.get(i)) != null) {
                            value1 = item.get(colMapper.get(i)).toString();
                        }
                        cel1.setCellValue(value1);
                    }
                }
                maxRow++;
            }

            //生成一个输出的文件,并将数据写入输出文件
            String outFileName = filePrex+KeyUtil.genUniqueKey() + ".xls";
            File outFile = new File(FileUtils.getFileRootPath() + appWeb.getTempFile() + File.separator + outFileName);
            ;
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            OutputStream os = new FileOutputStream(outFile);
            wb.write(os);

            //关闭输入流和输出流并返回文件的下载地址
            is.close();
            os.close();

            //插入操作日志
            OperateLog log1 = new OperateLog();
            log1.setLogType(1);
            log1.setDefault();
            log1.setUserName("admin");
            log1.setOperatTime(new Date());
            log1.setOperatContent("导出日志："+outFileName);
            this.operateLogService.save(log1);

            return HttpResultUtil.success(appWeb.getTempFile() + "/" + outFileName);
        }
    }
}
