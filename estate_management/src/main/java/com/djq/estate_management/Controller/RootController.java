package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Domain.Root;
import com.djq.estate_management.Dao.RootDao;
import com.djq.estate_management.Service.RootService;
import com.github.pagehelper.Page;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 超级管理员管理控制层控制器
 */
@RestController
@RequestMapping("/root")
public class RootController {


    @Autowired
    private RootDao rootDao;
    @Autowired
    private RootService rootService;
    @PostMapping("/login")
    public Result<Root> login(@RequestBody Root root ) {
        if (!checkParam(root)) {
            return Result.error(-1, "缺少必要参数");
        }

        Root dbUser = rootDao.selectByNameAndPasswordAndTelephone(root.getName(),Md5Crypt.apr1Crypt(root.getPassword(),"200316"),root.getTelephone());
        if (dbUser == null) {
            return Result.error(-1, "账号或密码错误");
        }
        return Result.success(dbUser);
    }

    private boolean checkParam(Root root) {
        return root.getName() != null && root.getPassword() != null && root.getTelephone() != null;
    }
    @RequestMapping("/find")
    public Result  find(){
        List<Root> all = rootService.findAll();
        return new Result (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Root> page = rootService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.ROOT_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result  add(@RequestBody Root root){
        Boolean add = rootService.add(root);
        return new Result (true,StatusCode.OK, MessageConstant.ROOT_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result  findById(Integer id){
        Root root=rootService.findById(id);
        return new Result (true,StatusCode.OK,MessageConstant.ROOT_FIND_BY_ID_SUCCESS,root);
    }
    @RequestMapping("/update")
    public Result  update(@RequestBody Root root){
        Boolean add = rootService.update(root);
        return new Result (true,StatusCode.OK, MessageConstant.ROOT_UPDATE_SUCCESS);
    }

    @RequestMapping("/updatePassword")
    public Result  updatePassword(@RequestBody Root root){
        root.setPassword(Md5Crypt.apr1Crypt(root.getPassword(),"200316"));
        root.setRepassword(Md5Crypt.apr1Crypt(root.getRepassword(),"200316"));
        Boolean add = rootService.update(root);
        return new Result (true,StatusCode.OK, MessageConstant.ROOT_UPDATE_SUCCESS);
    }

    @RequestMapping("/del")
    public Result  del(@RequestBody List<Integer> ids){
        Boolean flag= rootService.del(ids);
        return new Result (true,StatusCode.OK,MessageConstant.ROOT_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Root>list=rootDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\root_down_model.xls");
        ResponseUtil.export(response,wb,"超管信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /root/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Root>list,String templateFileUrl){

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
            for(Root root : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(root.getId());
                row.createCell(1).setCellValue(root.getName());
                row.createCell(2).setCellValue(root.getIdcard());
                row.createCell(3).setCellValue(root.getTelephone());
                row.createCell(4).setCellValue(DateUtil.formatDate(root.getBirthday(),"yyyy-MM-dd"));
                row.createCell(5).setCellValue(root.getSex());
                row.createCell(6).setCellValue(root.getRemark());
                row.createCell(7).setCellValue(DateUtil.formatDate(root.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));




            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

