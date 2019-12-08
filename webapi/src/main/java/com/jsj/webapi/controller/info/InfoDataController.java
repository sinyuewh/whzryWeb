package com.jsj.webapi.controller.info;

import com.jsj.common.bean.HttpResult;
import com.jsj.common.utils.HttpResultUtil;
import com.jsj.common.utils.KeyUtil;
import com.jsj.common.utils.MapUtil;
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
import com.jsj.webapi.utils.FileUtils;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.collections.BookmarkCollection;
import com.spire.doc.documents.BookmarksNavigator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.Range;
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
import java.net.URLDecoder;
import java.util.*;

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
            String abc=target.getAbc();
            BeanUtils.copyProperties(para,target);
            target.setAbc(abc);
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

    /**
     * 说明：成批的将数据导入到待报列表
     * @param ids
     * @return
     */
    @ApiOperation(value="成批的将数据导入到待报列表",notes="")
    @RequestMapping(value = "/infoToReportList",method = {RequestMethod.POST})
    public HttpResult infoToReportList(@ApiParam("数据id列表") @RequestParam(value = "ids")  String ids)
    {
        int id1= this.infoDataService.addReportList(ids);
        if(id1>0)
        {
            //插入操作日志
            OperateLog log1 = new OperateLog();
            log1.setLogType(1);
            log1.setDefault();
            log1.setUserName("admin");
            log1.setOperatTime(new Date());
            log1.setOperatContent("成批的将数据导入到待报列表："+ids);
            this.operateLogService.save(log1);
        }
        return HttpResultUtil.success("将数据导入到待报列表数据操作成功【"+id1+"】");
    }

    /**
     * 说明：成批设置数据的分类
     * @param ids
     * @return
     */
    @ApiOperation(value="成批设置数据的分类",notes="")
    @RequestMapping(value = "/setKind",method = {RequestMethod.POST})
    public HttpResult setKind(@ApiParam("数据id列表") @RequestParam(value = "ids")  String ids,
                              @ApiParam("数据分类") @RequestParam(value = "kind") String kind)
    {
        int id1= this.infoDataService.setMulInfoKind(ids,kind);
        if(id1>0)
        {
            //插入操作日志
            OperateLog log1 = new OperateLog();
            log1.setLogType(1);
            log1.setDefault();
            log1.setUserName("admin");
            log1.setOperatTime(new Date());
            log1.setOperatContent("设置数据【："+ids+"】的分类为"+kind);
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
            Map<Integer,String> map1=InfoData.getImportXlsMap(infoKind);
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
        if(p1.getContent().size()<=0)
        {
            return HttpResultUtil.error(900,"没有满足条件的数据！");
        }
        else
        {
            //定义模板和excel的映射关系
            Map<Integer,String> colMapper=InfoData.getExportXlsMap(para.getInfoKind());
            String filePrex=InfoData.getNameByInfoKind(para.getInfoKind());
            String modelFiledir=FileUtils.getFileRootPath()+appWeb.getTemplates()+ File.separator+InfoData.getExcelModelFileDir(para.getInfoKind());
            modelFiledir=URLDecoder.decode(modelFiledir,"UTF-8");

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
            log1.setOperatContent("导出:"+outFileName);
            this.operateLogService.save(log1);
            //关闭输入流和输出流并返回文件的下载地址
            is.close();
            os.close();

            return HttpResultUtil.success(appWeb.getTempFile() + "/" + outFileName);
        }
    }

    @ApiOperation(value="导出单条数据到Word",notes="")
    @PostMapping(value = "/exportToWord")
    public HttpResult exportToWord(@ApiParam("数据id") @RequestParam(value = "id")  int id ) throws Exception
    {
        InfoData d1=this.infoDataService.findOne(id);
        if(d1!=null)
        {
            InfoDTO para=new InfoDTO();
            BeanUtils.copyProperties(d1,para);
            //将数据填入word文档

            String filePrex= InfoData.getNameByInfoKind(para.getInfoKind());
            String modelFiledir=FileUtils.getFileRootPath()+appWeb.getTemplates()+ File.separator+ InfoData.getWordModelFileDir(para.getInfoKind());;
            modelFiledir=URLDecoder.decode(modelFiledir,"UTF-8");

            File modelFile = new File(modelFiledir);
            if(!modelFile.exists())
            {
                return HttpResultUtil.error(900,"项目导出的模板文件不存在！");
            }
            //定义字段
            List<String> list0=new ArrayList<>();
            for(int i=1;i<=30;i++)
            {
                list0.add("str"+i);
            }
            for(int i=1;i<=10;i++)
            {
                list0.add("txt"+i);
            }
            for(int i=1;i<=5;i++)
            {
                list0.add("time"+i);
            }
            //设置书签的内容
            String outFileName=this.setWordValue(modelFiledir,para,list0,filePrex);
            return HttpResultUtil.success(appWeb.getTempFile() + "/" + outFileName);
        }
        else
        {
            return HttpResultUtil.error(60,"得到数据明细失败！");
        }
    }

    /**
     * 接口说明：得到某个字段的唯一选项列表
     * @return
     */
    @ApiOperation(value = "字段列表数据",notes = "")
    @PostMapping(value = "/fieldList")
    public HttpResult fieldList(
            @ApiParam("数据类型") @RequestParam(value = "infoKind", required = true) String infoKind,
            @ApiParam("字段名称") @RequestParam(value = "fieldName", required = true) String fieldName
    ) throws Exception
    {
        Page p1=this.infoDataService.getFieldSearch(infoKind,fieldName);
        return HttpResultUtil.success(p1);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //利用com.spire.doc 设置doc控件的值
    private String setWordValue(String modelFiledir,InfoDTO para,
             List<String> list0,String filePrex) throws Exception
    {
        Document doc = new Document();
        //modelFiledir="E:\\武汉中瑞源信息管理系统\\whzry2019Solution\\whzryWeb\\webapi\\target\\classes\\static\\templates\\重点实验室.doc";
        modelFiledir=modelFiledir.replace("/",File.separator);
        if(modelFiledir.contains(":"))
        {
            modelFiledir=modelFiledir.substring(1);
        }
        doc.loadFromFile(modelFiledir);
        BookmarksNavigator bookmarksNavigator = new BookmarksNavigator(doc);
        Map<String,Object> map1=MapUtil.convertBean(para);

        BookmarkCollection bookmarkCollection = doc.getBookmarks();
        for(String m : list0) {
            if(bookmarkCollection.findByName(m)!=null) {
                bookmarksNavigator.moveToBookmark(m);
                //用文本内容替换原有书签位置的文本，新替换的内容与原文格式一致
                bookmarksNavigator.replaceBookmarkContent(map1.get(m).toString(),true);
            }
        }
        //保存文档
        String outFileName = filePrex+KeyUtil.genUniqueKey() + ".doc";
        String outFile =FileUtils.getFileRootPath() + appWeb.getTempFile() + File.separator + outFileName;
        outFile=URLDecoder.decode(outFile,"UTF-8");
        doc.saveToFile(outFile, FileFormat.Doc);

        //删除文档上的 Evaluation Warning: The document was created with Spire.Doc for JAVA.
        this.clearEvaluate(outFile);
        return outFileName;
    }

    //利用POI 设置doc控件的值
    private String setWordValue2(String modelFiledir,InfoDTO para,
                                List<String> list0,String filePrex) throws Exception
    {
        String outFileName="";
        if (modelFiledir.endsWith(".doc")) {
            InputStream is = new FileInputStream(new File(modelFiledir));
            HWPFDocument document = new HWPFDocument(is);
            Bookmarks bookmarks = document.getBookmarks();
            Map<String,Object> map1=MapUtil.convertBean(para);
            Integer size1=bookmarks.getBookmarksCount();
            for(int dwI = 0;dwI <size1 ;dwI++){
                Bookmark bookmark = bookmarks.getBookmark(dwI);
                String key1=bookmark.getName();
                if(list0.contains(key1)){
                    Range range = new Range(bookmark.getStart(),bookmark.getEnd(),document);
                    Object value1=map1.get(bookmark.getName());
                    if(MyStringUtil.isNotEmpty(value1))
                    {
                        range.replaceText(value1.toString(),false);
                    }
                    else
                    {
                        range.replaceText(" ",false);
                    }
                }
            }
            //保存文档
            outFileName = filePrex+KeyUtil.genUniqueKey() + ".doc";
            String outFile =FileUtils.getFileRootPath() + appWeb.getTempFile() + File.separator + outFileName;
            outFile=URLDecoder.decode(outFile,"UTF-8");
            OutputStream os = new FileOutputStream(outFile);
            document.write(os);

            //关闭文件流
            is.close();
            os.close();
        }
        return outFileName;
    }


    //利用POI 删除文档上的 Evaluation Warning: The document was created with Spire.Doc for JAVA.
    private String clearEvaluate(String modelFiledir) throws Exception
    {
        String evaluate="Evaluation Warning: The document was created with Spire.Doc for JAVA";
        String outFileName="";
        if (modelFiledir.endsWith(".doc")) {
            InputStream is = new FileInputStream(new File(modelFiledir));
            HWPFDocument document = new HWPFDocument(is);
            Range range=new Range(0,evaluate.length()+1,document);
            if(range!=null)
            {
                range.replaceText("",false);
            }
            OutputStream os = new FileOutputStream(modelFiledir);
            document.write(os);
            //关闭文件流
            is.close();
            os.close();
        }
        return outFileName;
    }
}
