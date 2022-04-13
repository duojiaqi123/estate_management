package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.VehicleDao;
import com.djq.estate_management.Domain.Personnel;
import com.djq.estate_management.Domain.Vehicle;
import com.djq.estate_management.Service.VehicleService;
import com.github.pagehelper.Page;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 车辆管理控制层控制器
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    
    @Autowired
        private VehicleService vehicleService;
    @Autowired
    private VehicleDao vehicleDao;
    @RequestMapping("/find")
    public Result    find(){
        List<Vehicle> all = vehicleService.findAll();
        return new Result   (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Vehicle> page = vehicleService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.VEHICLE_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result    add(@RequestBody Vehicle vehicle){
        Boolean add = vehicleService.add(vehicle);
        return new Result   (true,StatusCode.OK, MessageConstant.VEHICLE_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result    findById(Integer id){
        Vehicle vehicle=vehicleService.findById(id);
        return new Result   (true,StatusCode.OK,MessageConstant.VEHICLE_FIND_BY_ID_SUCCESS,vehicle);
    }
    @RequestMapping("/update")
    public Result    update(@RequestBody Vehicle vehicle){
        Boolean add = vehicleService.update(vehicle);
        return new Result   (true,StatusCode.OK, MessageConstant.VEHICLE_UPDATE_SUCCESS);
    }

    @RequestMapping("/del")
    public Result    del(@RequestBody List<Integer> ids){
        Boolean flag= vehicleService.del(ids);
        return new Result   (true,StatusCode.OK,MessageConstant.VEHICLE_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Vehicle>list=vehicleDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\vehicle_down_model.xls");
        ResponseUtil.export(response,wb,"车辆信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Vehicle>list,String templateFileUrl){

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
            for(Vehicle vehicle : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(vehicle.getId());
                row.createCell(1).setCellValue(vehicle.getPicture());
                row.createCell(2).setCellValue(vehicle.getOwnerId());
                row.createCell(3).setCellValue(vehicle.getOwnerName());
                row.createCell(4).setCellValue(vehicle.getParkingId());
                row.createCell(5).setCellValue(vehicle.getParkingCode());
                row.createCell(6).setCellValue(vehicle.getTelephone());
                row.createCell(7).setCellValue(vehicle.getColor());
                row.createCell(8).setCellValue(vehicle.getCarNumber());
                row.createCell(9).setCellValue(vehicle.getRemark());
                row.createCell(10).setCellValue(DateUtil.formatDate(vehicle.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));




            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

