package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.TradingStreamDao;
import com.djq.estate_management.Domain.TradingStream;
import com.djq.estate_management.Service.TradingStreamService;
import com.github.pagehelper.Page;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 交易流水管理控制层控制器
 */
@RestController
@RequestMapping("/tradingStream")
public class TradingStreamController {
    
    @Autowired
        private TradingStreamService tradingStreamService;

    @Autowired
    private TradingStreamDao tradingStreamDao;
    @RequestMapping("/find")
    public Result     find(){
        List<TradingStream> all = tradingStreamService.findAll();
        return new Result    (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<TradingStream> page = tradingStreamService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.TRADING_STREAM_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result     add(@RequestBody TradingStream tradingStream){
        Boolean add = tradingStreamService.add(tradingStream);
        return new Result    (true,StatusCode.OK, MessageConstant.TRADING_STREAM_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result     findById(Integer id){
        TradingStream tradingStream=tradingStreamService.findById(id);
        return new Result    (true,StatusCode.OK,MessageConstant.TRADING_STREAM_FIND_BY_ID_SUCCESS,tradingStream);
    }
    @RequestMapping("/update")
    public Result     update(@RequestBody TradingStream tradingStream){
        Boolean add = tradingStreamService.update(tradingStream);
        return new Result    (true,StatusCode.OK, MessageConstant.TRADING_STREAM_UPDATE_SUCCESS);
    }

    @RequestMapping("/del")
    public Result     del(@RequestBody List<Integer> ids){
        Boolean flag= tradingStreamService.del(ids);
        return new Result    (true,StatusCode.OK,MessageConstant.TRADING_STREAM_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<TradingStream>list=tradingStreamDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\tradingStream_down_model.xls");
        ResponseUtil.export(response,wb,"收费流水信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<TradingStream>list,String templateFileUrl){

        Workbook wb = null ;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templateFileUrl));
            wb = new HSSFWorkbook(fs);
            // 取得 模板的 第一个sheet 页
            Sheet sheet = wb.getSheetAt(0);
            // 拿到sheet页有 多少列
            int cellNums = sheet.getRow(0).getLastCellNum();
            // 从第2行 开搞    下标1  就是第2行
            int rowIndex = 1;
            Row row ;
            for(TradingStream tradingStream : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(tradingStream.getId());
                row.createCell(1).setCellValue(tradingStream.getChargeId());
                row.createCell(2).setCellValue(tradingStream.getOwnerId());
                row.createCell(3).setCellValue(tradingStream.getOwnerName());
                row.createCell(4).setCellValue(tradingStream.getTelephone());
                row.createCell(5).setCellValue(tradingStream.getPayMoney());
                row.createCell(6).setCellValue(tradingStream.getPayMoney());
                row.createCell(7).setCellValue(DateUtil.formatDate(tradingStream.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

