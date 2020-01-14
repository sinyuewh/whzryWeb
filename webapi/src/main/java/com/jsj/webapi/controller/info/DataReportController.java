package com.jsj.webapi.controller.info;/**
 * Created by jinshouji on 2018/10/19.
 */

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.common.utils.KeyUtil;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.webapi.config.AppWeb;
import com.jsj.webapi.dataobject.info.InfoData;
import com.jsj.webapi.dataobject.log.OperateLog;
import com.jsj.webapi.repository.info.ReportInfoDataRepository;
import com.jsj.webapi.service.info.InfoDataService;
import com.jsj.webapi.service.log.OperateLogService;
import com.jsj.webapi.utils.ExcelImportUtils;
import com.jsj.webapi.utils.FileEncryptAndDecrypt;
import com.jsj.webapi.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：jinshouji
 * 说明：数据上报接口
 **/

@Slf4j
@RestController
@Api(tags = {"5数据上报"}, description = "数据上报")
@RequestMapping(value = "/web/info")
public class DataReportController {

    //用来记录日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppWeb appWeb;

    @Autowired
    private InfoDataService infoDataService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private ReportInfoDataRepository reportInfoDataRepository;


    @ApiOperation(value = "得到待报数据的列表和数量",notes = "")
    @PostMapping(value = "/reportDataList")
    public HttpResult list()
    {
        String sql="select infoKind,infoName,count(*) infoCount from reportinfodata where status=0 group by infokind";
        Page p1=this.infoDataService.getPageListMapData(sql,-1,-1);
        return HttpResultUtil.success(p1);
    }


    @ApiOperation(value = "清理待报数据",notes = "")
    @PostMapping(value = "/clearReportinfo")
    public HttpResult clearReportinfo()
    {
        String sql="delete from reportinfodata where status=0";
        this.infoDataService.executeNativeSQL(sql);
        return HttpResultUtil.success();
    }



    @ApiOperation(value = "导出上报文件",notes = "")
    @PostMapping(value = "/exportReportFile")
    public HttpResult exportReportFile() throws Exception
    {
        String modelFiledir= FileUtils.getFileRootPath()+appWeb.getTemplates()+ File.separator+"上报信息.xls";
        modelFiledir= URLDecoder.decode(modelFiledir,"UTF-8");
        File modelFile = new File(modelFiledir);
        if(!modelFile.exists())
        {
            return HttpResultUtil.error(900,"项目导出的模板文件不存在！");
        }

        InputStream is = new FileInputStream(modelFile);
        Workbook wb = new HSSFWorkbook(is);
        //设置样式和字体
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

        for(int j=1;j<=7;j++)
        {
            if(j!=2)
            {
                String sql = "select * from infoData where id in (select parent from ReportInfoData where infokind='" + j + "') ";
                Page p1 = this.infoDataService.getPageListMapData(sql, -1, -1);

                Integer begRow = 1;
                if (p1.getContent().size() > 0) {
                    Map<Integer, String> colMapper = InfoData.getExportXlsMap(j + "");

                    Sheet sheet=null;
                    if(j==1) {
                        sheet=wb.getSheetAt(j - 1);
                    }
                    else {
                        sheet=wb.getSheetAt(j - 2);
                    }
                    List<Map<String, Object>> data1 = p1.getContent();
                    int maxRow = begRow;
                    for (Map<String, Object> item : data1) {
                        //根据条件得到数据，并将数据写入到sheet
                        Row row1 = sheet.getRow(maxRow);
                        if (row1 == null) {
                            row1 = sheet.createRow(maxRow);
                        }
                        //设置行高
                        row1.setHeight((short) (25 * 15.625));
                        for (int i = 0; i < colMapper.keySet().size() + 1; i++) {
                            Cell cel1 = row1.getCell(i);
                            if (cel1 == null) {
                                cel1 = row1.createCell(i);
                                cel1.setCellStyle(style);
                            }
                            if (i == 0) {
                                cel1.setCellValue(maxRow + "");
                            } else {
                                String value1 = "";
                                if (item.get(colMapper.get(i)) != null) {
                                    value1 = item.get(colMapper.get(i)).toString();
                                }
                                cel1.setCellValue(value1);
                            }
                        }
                        maxRow++;
                    }
                }
            }
        }

        //生成一个输出的文件,并将数据写入输出文件
        String outFileName = "上报文件"+ KeyUtil.genUniqueKey() + ".xls";
        String outFileName1=FileUtils.getFileRootPath() + appWeb.getTempFile() + File.separator + outFileName;
        outFileName1=URLDecoder.decode(outFileName1,"UTF-8");

        File outFile = new File( outFileName1);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        OutputStream os = new FileOutputStream(outFile);
        wb.write(os);

        //插入操作日志
        OperateLog log1 = new OperateLog();
        log1.setLogType(1);
        log1.setDefault();
        log1.setUserName("admin");
        log1.setOperatTime(new Date());
        log1.setOperatContent("导出上报文件");
        this.operateLogService.save(log1);

        //关闭输入流和输出流并返回文件的下载地址
        is.close();
        os.close();

        //对文件进行加密
        FileEncryptAndDecrypt.encrypt(outFileName1);
        Map<String,String> m1=new HashMap<>();
        m1.put("f1",appWeb.getTempFile() + "/" + outFileName);
        m1.put("f2",appWeb.getTempFile() + "/【密文】" + outFileName);

        return HttpResultUtil.success(m1);
    }


    @ApiOperation(value="导入上报文件数据",notes="")
    @PostMapping(value = "/importReportFile")
    public HttpResult importExcel(
            @ApiParam("数据文件") @RequestParam(value = "file", required = true) MultipartFile file,
            @ApiParam("上报单位") @RequestParam(value = "reportName", required = true) String reportName
            ) throws  Exception
    {
        if(file==null){
            return HttpResultUtil.error(900,"文件不能为空！");
        }
        //获取文件名
        String fileName=file.getOriginalFilename();
        //验证文件名是否合格
        if(!ExcelImportUtils.validateExcel(fileName))
        {
            return HttpResultUtil.error(900,"文件的格式必须是Excel格式！");
        }
        //进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(MyStringUtil.isNotEmpty(fileName)==false || size==0){
            return HttpResultUtil.error(900,"文件不能为空！");
        }

        //得到文件流到字节数组
        StringBuilder str=new StringBuilder();
        byte[] b1=file.getBytes();
        int pos1=b1.length-FileEncryptAndDecrypt.Key().length();
        for(int i =pos1; i<b1.length ; i++){
            str.append((char)b1[i]);
        }

        InputStream is = null;
        //判断是否为秘文，如果是，则需要进行脱秘处理
        if(str.toString().trim().equals(FileEncryptAndDecrypt.Key()))
        {
             String temp=this.writeToTempFile(b1);
             temp=FileEncryptAndDecrypt.decrypt(temp,temp+"01");
             File outFile = new File( temp);
             is = new FileInputStream(outFile);
        }
        else
        {
            is = file.getInputStream();
        }
        try
        {
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if(ExcelImportUtils.isExcel2007(fileName))
            {
                wb = new XSSFWorkbook(is);
            }else{
                wb = new HSSFWorkbook(is);
            }

            Integer begRow=1;
            String infoKind="0";
            for(int i=0;i<=5;i++) {
                //定义Excel与字段的映射关系
                if(i==0) infoKind="1";
                else infoKind=(i+2)+"";

                Map<Integer, String> map1 = InfoData.getImportXlsMap(infoKind);
                String error = this.infoDataService.ImportXlsToInfoDB(i,wb, infoKind, begRow, map1,reportName);
                if (MyStringUtil.isNotEmpty(error)) {
                    return HttpResultUtil.error(900, error);
                }
            }
            //插入操作日志
            OperateLog log1 = new OperateLog();
            log1.setLogType(1);
            log1.setDefault();
            log1.setUserName("admin");
            log1.setOperatTime(new Date());
            log1.setOperatContent("导入上报文件："+fileName);
            this.operateLogService.save(log1);

            //返回导入数据成功的标志
            return  HttpResultUtil.success();
        }
        catch(Exception e){
            e.printStackTrace();
        } finally
        {
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return HttpResultUtil.error(900,"导入出错！请检查数据格式！");
    }

    //将字节内容写入一个临时文件
    private String writeToTempFile(byte[] content) throws Exception
    {
        String outFileName1=FileUtils.getFileRootPath() + appWeb.getTempFile() + File.separator + KeyUtil.genUniqueKey();
        outFileName1=URLDecoder.decode(outFileName1,"UTF-8");
        File outFile = new File( outFileName1);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        OutputStream os = new FileOutputStream(outFile);
        os.write(content);
        os.close();
        return  outFileName1;
    }
}
