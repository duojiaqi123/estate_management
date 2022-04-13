package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.AdminDao;
import com.djq.estate_management.Domain.Admin;

import com.djq.estate_management.Service.AdminService;
import com.github.pagehelper.Page;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
 * 管理员管理控制层控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
        private AdminService adminService;
    @Autowired
    private AdminDao adminDao;
    @PostMapping("/login")
    public Result<Admin> login(@RequestBody Admin admin) {
        if (!checkParam(admin)) {
            return Result.error(-1, "缺少必要参数");
        }
        Admin dbUser = adminDao.selectByNameAndPasswordAndTelephone(admin.getName(), Md5Crypt.apr1Crypt(admin.getPassword(),"200316"),admin.getTelephone());
        if (dbUser == null) {
            return Result.error(-1, "账号或密码错误");
        }
        return Result.success(dbUser);
    }

    private boolean checkParam(Admin admin) {
        return admin.getName() != null && admin.getPassword() != null && admin.getTelephone() != null;
    }
    @RequestMapping("/find")
    public Result  find(){
        List<Admin> all = adminService.findAll();
        return new Result (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Admin> page = adminService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.ADMIN_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result  add(@RequestBody Admin admin){
        Boolean add = adminService.add(admin);
        return new Result (true,StatusCode.OK, MessageConstant.ADMIN_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result  findById(Integer id){
        Admin admin=adminService.findById(id);
        return new Result (true,StatusCode.OK,MessageConstant.ADMIN_FIND_BY_ID_SUCCESS,admin);
    }
    @RequestMapping("/update")
    public Result  update(@RequestBody Admin admin){
        Boolean add = adminService.update(admin);
        return new Result (true,StatusCode.OK, MessageConstant.ADMIN_UPDATE_SUCCESS);
    }

    @RequestMapping("/updatePassword")
    public Result  updatePassword(@RequestBody Admin admin){
        admin.setPassword(Md5Crypt.apr1Crypt(admin.getPassword(),"200316"));
        admin.setRepassword(Md5Crypt.apr1Crypt(admin.getRepassword(),"200316"));
        Boolean add = adminService.update(admin);
        return new Result (true,StatusCode.OK, MessageConstant.ADMIN_UPDATE_SUCCESS);
    }

    @RequestMapping("/del")
    public Result  del(@RequestBody List<Integer> ids){
        Boolean flag= adminService.del(ids);
        return new Result (true,StatusCode.OK,MessageConstant.ADMIN_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Admin>list=adminDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\admin_down_model.xls");
        ResponseUtil.export(response,wb,"管理员信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Admin>list,String templateFileUrl){

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
            for(Admin admin : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(admin.getId());
                row.createCell(1).setCellValue(admin.getName());
                row.createCell(2).setCellValue(admin.getIdcard());
                row.createCell(3).setCellValue(admin.getTelephone());
                row.createCell(4).setCellValue(DateUtil.formatDate(admin.getBirthday(),"yyyy-MM-dd"));
                row.createCell(5).setCellValue(admin.getSex());
                row.createCell(6).setCellValue(admin.getRemark());
                row.createCell(7).setCellValue(DateUtil.formatDate(admin.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));




            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

