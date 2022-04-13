package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.BuildingDao;
import com.djq.estate_management.Dao.PersonnelDao;
import com.djq.estate_management.Domain.Building;
import com.djq.estate_management.Domain.Personnel;
import com.djq.estate_management.Service.BuildingService;
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
 * 楼栋管理控制层控制器
 */
@RestController
@RequestMapping("/building")
public class BuildingController {
    
    @Autowired
        private BuildingService buildingService;
    @Autowired
    private BuildingDao buildingDao;
    @RequestMapping("/find")
    public Result   find(){
        List<Building> all = buildingService.findAll();
        return new Result  (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Building> page = buildingService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.BUILDING_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result   add(@RequestBody Building building){
        Boolean add = buildingService.add(building);
        return new Result  (true,StatusCode.OK, MessageConstant.BUILDING_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result   findById(Integer id){
        Building building=buildingService.findById(id);
        return new Result  (true,StatusCode.OK,MessageConstant.BUILDING_FIND_BY_ID_SUCCESS,building);
    }
    @RequestMapping("/update")
    public Result   update(@RequestBody Building building){
        Boolean add = buildingService.update(building);
        return new Result  (true,StatusCode.OK, MessageConstant.BUILDING_UPDATE_SUCCESS);
    }

    @RequestMapping("/del")
    public Result   del(@RequestBody List<Integer> ids){
        Boolean flag= buildingService.del(ids);
        return new Result  (true,StatusCode.OK,MessageConstant.BUILDING_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Building>list=buildingDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\building_down_model.xls");
        ResponseUtil.export(response,wb,"栋数管理信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Building>list,String templateFileUrl){

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
            for(Building building : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(building.getId());
                row.createCell(1).setCellValue(building.getName());
                row.createCell(2).setCellValue(building.getTotalHouseholds());
                row.createCell(3).setCellValue(building.getDescription());
                row.createCell(4).setCellValue(DateUtil.formatDate(building.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

