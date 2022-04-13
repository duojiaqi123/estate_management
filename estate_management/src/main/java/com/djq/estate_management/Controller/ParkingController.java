package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.ParkingDao;
import com.djq.estate_management.Dao.PersonnelDao;
import com.djq.estate_management.Domain.Parking;
import com.djq.estate_management.Domain.Personnel;
import com.djq.estate_management.Service.ParkingService;
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
 * 停车位管理控制层控制器
 */
@RestController
@RequestMapping("/parking")
public class ParkingController {
    
    @Autowired
        private ParkingService parkingService;
    @Autowired
    private ParkingDao parkingDao;
    @RequestMapping("/find")
    public Result  find(){
        List<Parking> all = parkingService.findAll();
        return new Result (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Parking> page = parkingService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.PARKING_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result  add(@RequestBody Parking parking){
        Boolean add = parkingService.add(parking);
        return new Result (true,StatusCode.OK, MessageConstant.PARKING_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result  findById(Integer id){
        Parking parking=parkingService.findById(id);
        return new Result (true,StatusCode.OK,MessageConstant.PARKING_FIND_BY_ID_SUCCESS,parking);
    }
    @RequestMapping("/update")
    public Result  update(@RequestBody Parking parking){
        Boolean add = parkingService.update(parking);
        return new Result (true,StatusCode.OK, MessageConstant.PARKING_UPDATE_SUCCESS);
    }
    //parking/updateStatus/0/1
    @RequestMapping("/updateStatus/{status}/{id}")
    public Result  updateStatus(@PathVariable("status") String status, @PathVariable("id") Integer id){
        Boolean flag= parkingService.updateStatus(status,id);
        return new Result (true,StatusCode.OK,MessageConstant.PARKING_UPDATE_STATUS_SUCCESS);
    }
    @RequestMapping("/del")
    public Result  del(@RequestBody List<Integer> ids){
        Boolean flag= parkingService.del(ids);
        return new Result (true,StatusCode.OK,MessageConstant.PARKING_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Parking>list=parkingDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\parking_down_model.xls");
        ResponseUtil.export(response,wb,"车位信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Parking>list,String templateFileUrl){

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
            for(Parking parking : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(parking.getId());
                row.createCell(1).setCellValue(parking.getCode());
                row.createCell(2).setCellValue(parking.getTotalFee());
                row.createCell(3).setCellValue(parking.getStatus());
                row.createCell(4).setCellValue(DateUtil.formatDate(parking.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

