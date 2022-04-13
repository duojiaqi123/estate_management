package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.ActivityDao;
import com.djq.estate_management.Dao.PersonnelDao;
import com.djq.estate_management.Domain.Activity;
import com.djq.estate_management.Domain.Personnel;
import com.djq.estate_management.Service.ActivityService;
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
 * 活动管理控制层控制器
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
    
    @Autowired
        private ActivityService activityService;
    @Autowired
    private ActivityDao activityDao;
    @RequestMapping("/find")
    public Result  find(){
        List<Activity> all = activityService.findAll();
        return new Result (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Activity> page = activityService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.ACTIVITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result  add(@RequestBody Activity activity){
        Boolean add = activityService.add(activity);
        return new Result (true,StatusCode.OK, MessageConstant.ACTIVITY_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result  findById(Integer id){
        Activity activity=activityService.findById(id);
        return new Result (true,StatusCode.OK,MessageConstant.ACTIVITY_FIND_BY_ID_SUCCESS,activity);
    }
    @RequestMapping("/update")
    public Result  update(@RequestBody Activity activity){
        Boolean add = activityService.update(activity);
        return new Result (true,StatusCode.OK, MessageConstant.ACTIVITY_UPDATE_SUCCESS);
    }
    //activity/updateStatus/0/1
    @RequestMapping("/updateStatus/{status}/{id}")
    public Result  updateStatus(@PathVariable("status") String status, @PathVariable("id") Integer id){
        Boolean flag= activityService.updateStatus(status,id);
        return new Result (true,StatusCode.OK,MessageConstant.ACTIVITY_UPDATE_STATUS_SUCCESS);
    }
    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag= activityService.del(ids);
        return new Result(true,StatusCode.OK,MessageConstant.ACTIVITY_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Activity>list= activityDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\activity_down_model.xls");
        ResponseUtil.export(response,wb,"活动信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Activity>list,String templateFileUrl){

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
            for(Activity activity : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(activity.getId());
                row.createCell(1).setCellValue(activity.getTitle());
                row.createCell(2).setCellValue(activity.getAddress());
                row.createCell(3).setCellValue(activity.getOrganizer());
                row.createCell(4).setCellValue(activity.getStatus());
                row.createCell(5).setCellValue(DateUtil.formatDate(activity.getStartTime(),"yyyy-MM-dd"));
                row.createCell(6).setCellValue(DateUtil.formatDate(activity.getEndTime(), "yyyy-MM-dd"));




            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}
