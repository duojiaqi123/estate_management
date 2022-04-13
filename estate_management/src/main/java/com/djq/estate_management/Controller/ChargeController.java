package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.ChargeDao;
import com.djq.estate_management.Dao.PersonnelDao;
import com.djq.estate_management.Domain.Charge;
import com.djq.estate_management.Domain.Personnel;
import com.djq.estate_management.Service.ChargeService;
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
 * 缴费管理控制层控制器
 */
@RestController
@RequestMapping("/charge")
public class ChargeController {
    
    @Autowired
        private ChargeService chargeService;
    @Autowired
    private ChargeDao chargeDao;
    @RequestMapping("/find")
    public Result    find(){
        List<Charge> all = chargeService.findAll();
        return new Result   (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Charge> page = chargeService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.CHARGE_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/searchTrading")
    public PageResult searchTrading(@RequestBody Map searchTradingMap){
        Page<Charge> page = chargeService.search(searchTradingMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.CHARGE_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result    add(@RequestBody Charge charge){
        Boolean add = chargeService.add(charge);
        return new Result   (true,StatusCode.OK, MessageConstant.CHARGE_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result    findById(Integer id){
        Charge charge=chargeService.findById(id);
        return new Result   (true,StatusCode.OK,MessageConstant.CHARGE_FIND_BY_ID_SUCCESS,charge);
    }
    @RequestMapping("/update")
    public Result    update(@RequestBody Charge charge){
        Boolean add = chargeService.update(charge);
        return new Result   (true,StatusCode.OK, MessageConstant.CHARGE_UPDATE_SUCCESS);
    }
    //charge/updateStatus/0/1
    @RequestMapping("/updateStatus/{status}/{id}")
    public Result    updateStatus(@PathVariable("status") String status, @PathVariable("id") Integer id){
        Boolean flag= chargeService.updateStatus(status,id);
        return new Result   (true,StatusCode.OK,MessageConstant.CHARGE_UPDATE_STATUS_SUCCESS);
    }
    @RequestMapping("/del")
    public Result    del(@RequestBody List<Integer> ids){
        Boolean flag= chargeService.del(ids);
        return new Result   (true,StatusCode.OK,MessageConstant.CHARGE_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Charge>list=chargeDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\charge_down_model.xls");
        ResponseUtil.export(response,wb,"收费信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Charge>list,String templateFileUrl){

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
            for(Charge charge : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(charge.getId());
                row.createCell(1).setCellValue(charge.getDetail());
                row.createCell(2).setCellValue(charge.getContractor());
                row.createCell(3).setCellValue(charge.getOwnerId());
                row.createCell(4).setCellValue(charge.getOwnerName());
                row.createCell(5).setCellValue(charge.getTelephone());
                row.createCell(6).setCellValue(charge.getDetail());
                row.createCell(7).setCellValue(charge.getPayMoney());
                row.createCell(8).setCellValue(charge.getStatus());
                row.createCell(9).setCellValue(DateUtil.formatDate(charge.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));





            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

