package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.HomeDao;
import com.djq.estate_management.Dao.PersonnelDao;
import com.djq.estate_management.Domain.Home;
import com.djq.estate_management.Domain.Personnel;
import com.djq.estate_management.Service.HomeService;
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
 * 房产管理控制层控制器
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    
    @Autowired
        private HomeService homeService;
    @Autowired
    private HomeDao homeDao;
    @RequestMapping("/find")
    public Result  find(){
        List<Home> all = homeService.findAll();
        return new Result (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Home> page = homeService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.HOME_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result  add(@RequestBody Home home){
        Boolean add = homeService.add(home);
        return new Result (true,StatusCode.OK, MessageConstant.HOME_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result  findById(Integer id){
        Home home=homeService.findById(id);
        return new Result (true,StatusCode.OK,MessageConstant.HOME_FIND_BY_ID_SUCCESS,home);
    }
    @RequestMapping("/update")
    public Result  update(@RequestBody Home home){
        Boolean add = homeService.update(home);
        return new Result (true,StatusCode.OK, MessageConstant.HOME_UPDATE_SUCCESS);
    }

    @RequestMapping("/del")
    public Result  del(@RequestBody List<Integer> ids){
        Boolean flag= homeService.del(ids);
        return new Result (true,StatusCode.OK,MessageConstant.HOME_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Home>list=homeDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\home_down_model.xls");
        ResponseUtil.export(response,wb,"房产信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Home>list,String templateFileUrl){

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
            for(Home home : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(home.getId());
                row.createCell(1).setCellValue(home.getBuildingId());
                row.createCell(2).setCellValue(home.getBuildingName());
                row.createCell(3).setCellValue(home.getCode());
                row.createCell(4).setCellValue(home.getName());
                row.createCell(5).setCellValue(home.getRoomNum());
                row.createCell(6).setCellValue(home.getUnit());
                row.createCell(7).setCellValue(home.getFloor());
                row.createCell(8).setCellValue(home.getDescription());
                row.createCell(9).setCellValue(DateUtil.formatDate(home.getLiveTime(), "yyyy-MM-dd HH:mm:ss"));




            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

