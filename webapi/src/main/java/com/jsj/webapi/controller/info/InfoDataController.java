package com.jsj.webapi.controller.info;

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.common.utils.KeyUtil;
import com.jsj.common.utils.MyStringUtil;
import com.jsj.webapi.config.AppWeb;
import com.jsj.webapi.config.ServerConfig;
import com.jsj.webapi.dataobject.info.InfoData;
import com.jsj.webapi.dataobject.log.OperateLog;
import com.jsj.webapi.dto.info.InfoDTO;
import com.jsj.webapi.enums.ErrorEnum;
import com.jsj.webapi.exception.MyException;
import com.jsj.webapi.service.info.InfoDataService;
import com.jsj.webapi.service.log.OperateLogService;
import com.jsj.webapi.utils.ExcelImportUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Slf4j
@RestController
@Api(tags = {"1信息管理"}, description = "信息管理")
@RequestMapping(value = "/web/info")
public class InfoDataController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private InfoDataService infoDataService;

    @Autowired
    private AppWeb appWeb;
    @Autowired
    private OperateLogService operateLogService;
    /**
     * 接口说明：增加一个信息数据
     * @param para
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "新增数据",notes = "")
    @PostMapping(value = "/create")
    public HttpResult create(@ApiParam("信息对象json") @RequestBody @Valid InfoDTO para, BindingResult bindingResult)
    {
        //验证数据的是否正确
        if (bindingResult.hasErrors()) {
            log.error("【信息数据】参数不正确, orderForm={}", para);
            throw new MyException(ErrorEnum.Lift_Error.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //创建对象和赋值
        InfoData target=new InfoData();
        BeanUtils.copyProperties(para,target);
        target.setDefault();

        //保存数据
        target=infoDataService.save(target);
        Map<String, String> map = new HashMap<>();
        map.put("infoID", target.getId()+"");

        //插入操作日志
        OperateLog log1 = new OperateLog();
        log1.setLogType(1);
        log1.setDefault();
        log1.setUserName("admin");
        log1.setOperatTime(new Date());
        log1.setOperatContent("新增数据类型："+para.getInfoKind()+",id："+para.getId());
        this.operateLogService.save(log1);

        return  HttpResultUtil.success(map);
    }


    @ApiOperation(value = "更新数据",notes = "")
    @PostMapping(value = "/update")
    public HttpResult update(@ApiParam("信息对象json") @RequestBody @Valid InfoDTO para, BindingResult bindingResult)
    {
        //验证数据的是否正确
        if (bindingResult.hasErrors()) {
            log.error("【信息数据】参数不正确, orderForm={}", para);
            throw new MyException(ErrorEnum.Lift_Error.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //创建对象和赋值
        InfoData target=infoDataService.findOne(para.getId());
        if(target==null)
        {
            target=new InfoData();
            BeanUtils.copyProperties(para,target);
            target.setDefault();
        }
        else {
            BeanUtils.copyProperties(para,target);
        }
        //保存数据
        target=infoDataService.save(target);
        Map<String, String> map = new HashMap<>();
        map.put("infoID", target.getId()+"");

        //插入操作日志
        OperateLog log1 = new OperateLog();
        log1.setLogType(1);
        log1.setDefault();
        log1.setUserName("admin");
        log1.setOperatTime(new Date());
        log1.setOperatContent("更新数据类型："+para.getInfoKind()+",id："+para.getId());
        this.operateLogService.save(log1);

        return  HttpResultUtil.success(map);
    }


    /**
     * 接口说明：增加一个信息数据
     * @param para
     * @return
     */
    @ApiOperation(value = "列表数据",notes = "")
    @PostMapping(value = "/list")
    public HttpResult List(@ApiParam("查询条件") InfoDTO para) throws Exception
    {
        Page p1=this.infoDataService.getSearch(para,1,appWeb.getPageSize());

        //插入操作日志
        OperateLog log1 = new OperateLog();
        log1.setLogType(1);
        log1.setDefault();
        log1.setUserName("admin");
        log1.setOperatTime(new Date());
        log1.setOperatContent("查询数据类型："+para.getInfoKind()+",id："+para.getId());
        this.operateLogService.save(log1);

        return HttpResultUtil.success(p1);
    }


    /**
     * 接口说明：得到区域的接口
     * @param infoKind 信息的类别
     * @return
     */
    @ApiOperation(value = "区域接口",notes = "")
    @PostMapping(value = "/areaList")
    public HttpResult areaList( @ApiParam("数据类别") @RequestParam(value = "infoKind")  String infoKind ,
                                @ApiParam("字段类别") @RequestParam(value = "str") String  str
                                    ) throws Exception
    {
        InfoDTO para=new InfoDTO();
        para.setInfoKind(infoKind);
        String fields=str;
        /*if(infoKind.equals("10"))
        {
            fields="str10";
        }*/
        Page p1=this.infoDataService.getSearch(para,fields,-1,-1);
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
        InfoData d1=this.infoDataService.findOne(id);
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
        int id1= this.infoDataService.deletMulDevice(ids);
        if(id1>0)
        {
            //插入操作日志
            OperateLog log1 = new OperateLog();
            log1.setLogType(1);
            log1.setDefault();
            log1.setUserName("admin");
            log1.setOperatTime(new Date());
            log1.setOperatContent("删除id："+ids);
            this.operateLogService.save(log1);

            return HttpResultUtil.success();
        }
        else
        {
            return HttpResultUtil.error(40,"删除数据操作失败！");
        }
    }


    @ApiOperation(value="导入数据",notes="")
    @PostMapping(value = "/import")
    public HttpResult importExcel(
            @ApiParam("数据文件") @RequestParam(value = "file", required = false) MultipartFile file,
            @ApiParam("数据类型") @RequestParam(value = "infoKind", required = false) String infoKind )
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
        InputStream is = null;
        try
        {
            is = file.getInputStream();
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if(ExcelImportUtils.isExcel2007(fileName))
            {
                wb = new XSSFWorkbook(is);
            }else{
                wb = new HSSFWorkbook(is);
            }

            Integer begRow=1;

            //定义Excel与字段的映射关系
            Map<Integer,String> map1=new HashedMap();
            if(infoKind.equals("1"))
            {
                map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"txt1");
                map1.put(5,"txt2");map1.put(6,"txt3");map1.put(7,"str7");map1.put(8,"str8");
            }
            else if(infoKind.equals("2"))
            {
                map1.put(1,"str1");map1.put(2,"str3");map1.put(3,"str4");
                map1.put(4,"str5");map1.put(5,"str6");map1.put(6,"str7");map1.put(7,"str8");
            }
            else if(infoKind.equals("3"))
            {
                map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
                map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"txt1");map1.put(8,"txt2");
                map1.put(9,"str9");map1.put(10,"str10");
            }
            else if(infoKind.equals("4"))
            {
                map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
                map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
                map1.put(9,"txt1");map1.put(10,"txt2");map1.put(11,"str11");map1.put(12,"str12");
            }
            else if(infoKind.equals("5"))
            {
                map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
                map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
                map1.put(9,"str9");map1.put(10,"str10");map1.put(11,"str11");map1.put(12,"str12");
                map1.put(13,"txt1");map1.put(14,"txt2");map1.put(15,"str15");map1.put(16,"str16");
            }
            else if(infoKind.equals("6"))
            {
                map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
                map1.put(5,"str5");map1.put(6,"txt1");map1.put(7,"txt2");map1.put(8,"str8");
                map1.put(9,"str9");
            }
            else if(infoKind.equals("7"))
            {
                map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
                map1.put(5,"str5");map1.put(6,"txt1");map1.put(7,"txt2");map1.put(8,"str8");
                map1.put(9,"str9");
            }
            else if(infoKind.equals("8"))
            {
                map1.put(1,"str1");map1.put(2,"str2");map1.put(3,"str3");map1.put(4,"str4");
                map1.put(5,"str5");map1.put(6,"str6");map1.put(7,"str7");map1.put(8,"str8");
                map1.put(9,"str9");
            }

            String error=this.infoDataService.ImportXlsToInfoDB(wb,infoKind,begRow,map1);
            if(MyStringUtil.isNotEmpty(error))
            {
                return HttpResultUtil.error(900,error);
            }

            //插入操作日志
            OperateLog log1 = new OperateLog();
            log1.setLogType(1);
            log1.setDefault();
            log1.setUserName("admin");
            log1.setOperatTime(new Date());
            log1.setOperatContent("导入文件："+fileName+",数据类型："+infoKind);
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


    @ApiOperation(value="导出Excel",notes="")
    @PostMapping(value = "/export")
    public HttpResult exportToExcel(@ApiParam("查询条件") InfoDTO para ) throws Exception
    {
        Page p1=this.infoDataService.getSearch(para,1,10000);
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
            if(para.getInfoKind().equals("1"))
            {
                modelFiledir=appWeb.getFileRootPath()+appWeb.getTemplates()+ File.separator+"重点区域.xls";
                filePrex="重点区域";
                begRow=1;
                for(int i=1;i<=8;i++) {
                    if(i>3&&i<7){

                        colMapper.put(i,"txt"+(i-3));
                    }
                    else colMapper.put(i,"str"+i);
                }
            }
            else if(para.getInfoKind().equals("2"))
            {
                modelFiledir=appWeb.getFileRootPath()+appWeb.getTemplates()+ File.separator+"重点领域.xls";
                filePrex="重点领域";
                begRow=1;
                colMapper.put(1,"str1");colMapper.put(2,"str3");colMapper.put(3,"str4");
                colMapper.put(4,"str5");colMapper.put(5,"str6");colMapper.put(6,"str7");colMapper.put(7,"str8");
            }
            else if(para.getInfoKind().equals("3"))
            {
                modelFiledir=appWeb.getFileRootPath()+appWeb.getTemplates()+ File.separator+"重点实验室.xls";
                begRow=1;
                for(int i=1;i<=10;i++) {
                    if(i>6&&i<9){

                        colMapper.put(i,"txt"+(i-6));
                    }
                    else colMapper.put(i,"str"+i);
                }

                filePrex="重点实验室";
            }
            else if(para.getInfoKind().equals("4"))
            {
                modelFiledir=appWeb.getFileRootPath()+appWeb.getTemplates()+ File.separator+"重点项目.xls";
                begRow=1;
                for(int i=1;i<=12;i++) {
                    if(i>8&&i<11){

                        colMapper.put(i,"txt"+(i-8));
                    }
                    else colMapper.put(i,"str"+i);
                }
                filePrex="重点项目";
            }
            else if(para.getInfoKind().equals("5"))
            {
                modelFiledir=appWeb.getFileRootPath()+appWeb.getTemplates()+ File.separator+"重点企业.xls";
                begRow=1;
                for(int i=1;i<=16;i++) {
                    if(i>12&&i<15){
                        colMapper.put(i,"txt"+(i-12));
                    }
                    else colMapper.put(i,"str"+i);
                }
                filePrex="重点企业";
            }
            else if(para.getInfoKind().equals("6"))
            {
                modelFiledir=appWeb.getFileRootPath()+appWeb.getTemplates()+ File.separator+"重点高校.xls";
                begRow=1;
                for(int i=1;i<=9;i++) {
                    if(i>5&&i<8){
                        colMapper.put(i,"txt"+(i-5));
                    }
                    else colMapper.put(i,"str"+i);
                }
                filePrex="重点高校";
            }
            else if(para.getInfoKind().equals("7"))
            {
                modelFiledir=appWeb.getFileRootPath()+appWeb.getTemplates()+ File.separator+"金融机构.xls";
                begRow=1;
                for(int i=1;i<=9;i++) {
                    if(i>5&&i<8){
                        colMapper.put(i,"txt"+(i-5));
                    }
                    else colMapper.put(i,"str"+i);
                }
                filePrex="金融机构";
            }
            else if(para.getInfoKind().equals("8"))
            {
                modelFiledir=appWeb.getFileRootPath()+appWeb.getTemplates()+ File.separator+"通讯录.xls";
                begRow=1;
                for(int i=1;i<=9;i++) colMapper.put(i,"str"+i);
                filePrex="通讯录";
            }

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
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

            // 设置边框
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

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
            File outFile = new File(appWeb.getFileRootPath() + appWeb.getTempFile() + File.separator + outFileName);
            ;
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
            log1.setOperatContent("导出:"+outFileName);
            this.operateLogService.save(log1);
            //关闭输入流和输出流并返回文件的下载地址
            is.close();
            os.close();

            return HttpResultUtil.success(appWeb.getTempFile() + "/" + outFileName);
        }
    }
}
