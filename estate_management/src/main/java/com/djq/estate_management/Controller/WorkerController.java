package com.djq.estate_management.Controller;

import com.djq.estate_management.Common.*;
import com.djq.estate_management.Dao.WorkerDao;
import com.djq.estate_management.Domain.Worker;
import com.djq.estate_management.Service.WorkerService;
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
 * 维修工管理控制层控制器
 */
@RestController
@RequestMapping("/worker")
public class WorkerController {
    
    @Autowired
        private WorkerService workerService;
    @Autowired
    private WorkerDao workerDao;
    @PostMapping("/login")
    public Result<Worker> login(@RequestBody Worker worker) {
        if (!checkParam(worker)) {
            return Result.error(-1, "缺少必要参数");
        }
        Worker dbUser = workerDao.selectByNameAndPasswordAndTelephone(worker.getName(), Md5Crypt.apr1Crypt(worker.getPassword(),"200316"),worker.getTelephone());
        if (dbUser == null) {
            return Result.error(-1, "账号或密码错误");
        }
        return Result.success(dbUser);
    }

    private boolean checkParam(Worker worker) {
        return worker.getName() != null && worker.getPassword() != null && worker.getTelephone() != null;
    }
    @RequestMapping("/find")
    public Result  find(){
        List<Worker> all = workerService.findAll();
        return new Result (false,200,"请求成功",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Worker> page = workerService.search(searchMap);
        return new PageResult(true, StatusCode.OK,MessageConstant.WORKER_SEARCH_SUCCESS,page.getResult(),page.getTotal());//page.getTotal()获取总条数
    }
    @RequestMapping("/add")
    public Result  add(@RequestBody Worker worker){
        Boolean add = workerService.add(worker);
        return new Result (true,StatusCode.OK, MessageConstant.WORKER_ADD_SUCCESS);
    }
    @RequestMapping("findById")
    public Result  findById(Integer id){
        Worker worker=workerService.findById(id);
        return new Result (true,StatusCode.OK,MessageConstant.WORKER_FIND_BY_ID_SUCCESS,worker);
    }

    @RequestMapping("/update")
    public Result  update(@RequestBody Worker worker){
        Boolean add = workerService.update(worker);
        return new Result (true,StatusCode.OK, MessageConstant.WORKER_UPDATE_SUCCESS);
    }

    @RequestMapping("/updatePassword")
    public Result  updatePassword(@RequestBody Worker worker){
        worker.setPassword(Md5Crypt.apr1Crypt(worker.getPassword(),"200316"));
        worker.setRepassword(Md5Crypt.apr1Crypt(worker.getRepassword(),"200316"));
        Boolean add = workerService.update(worker);
        return new Result (true,StatusCode.OK, MessageConstant.WORKER_UPDATE_SUCCESS);
    }

    @RequestMapping("/del")
    public Result  del(@RequestBody List<Integer> ids){
        Boolean flag= workerService.del(ids);
        return new Result (true,StatusCode.OK,MessageConstant.WORKER_DELETE_SUCCESS);
    }
    @RequestMapping("/excel_download")
    public String excel_download(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        List<Worker>list=workerDao.selectAll();
        Workbook wb=fillExcelDataWithTemplate(list,"C:\\djq_project\\estate_management\\src\\main\\resources\\static\\excel_demo\\worker_down_model.xls");
        ResponseUtil.export(response,wb,"维修工信息.xls");
        return null;
    }


    /**
     * @param templateFileUrl
     *            excel模板的路径 /worker/page/JYZ/client/client_info.xls
     * @return
     */

    public static Workbook fillExcelDataWithTemplate(List<Worker>list,String templateFileUrl){

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
            for(Worker worker : list){
                row = sheet.createRow(rowIndex);
                rowIndex ++;
                row.createCell(0).setCellValue(worker.getId());
                row.createCell(1).setCellValue(worker.getName());
                row.createCell(2).setCellValue(worker.getIdcard());
                row.createCell(3).setCellValue(worker.getTelephone());
                row.createCell(4).setCellValue(DateUtil.formatDate(worker.getBirthday(),"yyyy-MM-dd"));
                row.createCell(5).setCellValue(worker.getSex());
                row.createCell(6).setCellValue(worker.getRemark());
                row.createCell(7).setCellValue(DateUtil.formatDate(worker.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}

