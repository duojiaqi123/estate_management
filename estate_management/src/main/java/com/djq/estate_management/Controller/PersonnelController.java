package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.MessageConstant;
import com.djq.estate_management.Common.PageResult;
import com.djq.estate_management.Common.Result;
import com.djq.estate_management.Common.StatusCode;
import com.djq.estate_management.Dao.PersonnelDao;
import com.djq.estate_management.Domain.Personnel;
import com.djq.estate_management.Service.PersonnelService;
import com.djq.estate_management.Common.ResponseUtil;
import com.djq.estate_management.Common.DateUtil;
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
 * 业主管理控制层控制器
 */
@RestController
@RequestMapping("/personnel")
public class PersonnelController {
    
    @Autowired
        private PersonnelService personnelService;
    @Autowired
    private PersonnelDao personnelDao;
    @PostMapping("/login")
    public Result<Personnel> login(@RequestBody Personnel personnel) {
        if (!checkParam(personnel)) {
            return Result.error(-1, "缺少必要参数");
        }
        Personnel dbUser = personnelDao.selectByNameAndPasswordAndTelephone(personnel.getName(), Md5Crypt.apr1Crypt(personnel.getPassword(),"200316"),personnel.getTelephone());
        if (dbUser == null) {
            return Result.error(-1, "账号或密码错误");
        }
        return Result.success(dbUser);
    }
    private boolean checkParam(Personnel personnel) {
        return personnel.getName() != null && personnel.getPassword() != null && personnel.getTelephone() != null;
    }
    @RequestMapping("/find")
    public Result  find(){
        List<Personnel> all = personnelService.findAll();
        return new Result (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Personnel> page = personnelService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.PERSONNEL_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result  add(@RequestBody Personnel personnel){
        Boolean add = personnelService.add(personnel);
        return new Result (true,StatusCode.OK, MessageConstant.PERSONNEL_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result  findById(Integer id){
        Personnel personnel=personnelService.findById(id);
        return new Result (true,StatusCode.OK,MessageConstant.PERSONNEL_FIND_BY_ID_SUCCESS,personnel);
    }

    @RequestMapping("/update")
    public Result  update(@RequestBody Personnel personnel){
        Boolean add = personnelService.update(personnel);
        return new Result (true,StatusCode.OK, MessageConstant.PERSONNEL_UPDATE_SUCCESS);
    }

    @RequestMapping("/updatePassword")
    public Result  updatePassword(@RequestBody Personnel personnel){
        personnel.setPassword(Md5Crypt.apr1Crypt(personnel.getPassword(),"200316"));
        personnel.setRepassword(Md5Crypt.apr1Crypt(personnel.getRepassword(),"200316"));
        Boolean add = personnelService.update(personnel);
        return new Result (true,StatusCode.OK, MessageConstant.PERSONNEL_UPDATE_SUCCESS);
    }
    @RequestMapping("/del")
    public Result  del(@RequestBody List<Integer> ids){
        Boolean flag= personnelService.del(ids);
        return new Result (true,StatusCode.OK,MessageConstant.PERSONNEL_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
  throws Exception {
            List<Personnel>list=personnelDao.selectAll();
            Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\personal_down_model.xls");
            ResponseUtil.export(response,wb,"人员信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Personnel>list,String templateFileUrl){

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
                for(Personnel personnel : list){
                    row = sheet.createRow(rowIndex);
                    rowIndex ++;
                    row.createCell(0).setCellValue(personnel.getId());
                    row.createCell(1).setCellValue(personnel.getHouseId());
                    row.createCell(2).setCellValue(personnel.getHouseName());
                    row.createCell(3).setCellValue(personnel.getName());
                    row.createCell(4).setCellValue(personnel.getIdcard());
                    row.createCell(5).setCellValue(personnel.getTelephone());
                    row.createCell(6).setCellValue(DateUtil.formatDate(personnel.getBirthday(),"yyyy-MM-dd"));
                    row.createCell(7).setCellValue(personnel.getSex());
                    row.createCell(8).setCellValue(personnel.getType());
                    row.createCell(9).setCellValue(personnel.getRemark());
                    row.createCell(10).setCellValue(DateUtil.formatDate(personnel.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));




                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return wb;
        }
}
