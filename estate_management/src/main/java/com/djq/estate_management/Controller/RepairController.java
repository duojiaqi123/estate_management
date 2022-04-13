package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.RepairDao;
import com.djq.estate_management.Domain.Repair;
import com.djq.estate_management.Service.RepairService;
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
 * 报修管理控制层控制器
 */
@RestController
@RequestMapping("/repair")
public class RepairController {
    
    @Autowired
        private RepairService repairService;
    @Autowired
    private RepairDao repairDao;
    @RequestMapping("/find")
    public Result    find(){
        List<Repair> all = repairService.findAll();
        return new Result   (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Repair> page = repairService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.REPAIR_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result    add(@RequestBody Repair repair){
        Boolean add = repairService.add(repair);
        return new Result   (true,StatusCode.OK, MessageConstant.REPAIR_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result    findById(Integer id){
        Repair repair=repairService.findById(id);
        return new Result   (true,StatusCode.OK,MessageConstant.REPAIR_FIND_BY_ID_SUCCESS,repair);
    }
    @RequestMapping("/update")
    public Result    update(@RequestBody Repair repair){
        Boolean add = repairService.update(repair);
        return new Result   (true,StatusCode.OK, MessageConstant.REPAIR_UPDATE_SUCCESS);
    }
    //repair/updateStatus/0/1
    @RequestMapping("/updateStatus/{status}/{id}")
    public Result    updateStatus(@PathVariable("status") String status, @PathVariable("id") Integer id){
        Boolean flag= repairService.updateStatus(status,id);
        return new Result   (true,StatusCode.OK,MessageConstant.REPAIR_UPDATE_STATUS_SUCCESS);
    }
    @RequestMapping("/del")
    public Result    del(@RequestBody List<Integer> ids){
        Boolean flag= repairService.del(ids);
        return new Result   (true,StatusCode.OK,MessageConstant.REPAIR_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Repair>list=repairDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\repair_down_model.xls");
        ResponseUtil.export(response,wb,"报修信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Repair>list,String templateFileUrl){

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
            for(Repair repair : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(repair.getId());
                row.createCell(1).setCellValue(repair.getHouseId());
                row.createCell(2).setCellValue(repair.getHouseName());
                row.createCell(3).setCellValue(repair.getOwnerId());
                row.createCell(4).setCellValue(repair.getOwnerName());
                row.createCell(5).setCellValue(repair.getTelephone());
                row.createCell(6).setCellValue(repair.getDescription());
                row.createCell(7).setCellValue(repair.getResult());
                row.createCell(8).setCellValue(repair.getStatus());
                row.createCell(9).setCellValue(DateUtil.formatDate(repair.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));




            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

