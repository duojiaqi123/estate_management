package com.djq.estate_management.Service.impl;


import com.djq.estate_management.Dao.AdminDao;
import com.djq.estate_management.Dao.WorkerDao;
import com.djq.estate_management.Domain.Admin;
import com.djq.estate_management.Domain.Worker;
import com.djq.estate_management.Service.AdminService;
import com.djq.estate_management.Service.WorkerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerDao workerDao;

    @Override
    public List<Worker> findAll() {
        List<Worker> workers = workerDao.selectAll();
        return workers;
    }

    @Override
    public Page<Worker> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Worker.class);//指定查询的表tb_worker
        //初始化分页条件
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap !=null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件
            //时间区间
            if(StringUtil.isNotEmpty((String) searchMap.get("startTime"))){
                criteria.andGreaterThanOrEqualTo("createTime",searchMap.get("startTime"));
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("endTime"))){
                criteria.andLessThanOrEqualTo("createTime",searchMap.get("endTime"));
            }
            //名称模糊查询
            if(StringUtil.isNotEmpty((String) searchMap.get("name"))){
                criteria.andLike("name", "%"+(String) searchMap.get("name")+"%");
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("telephone"))){
                criteria.andLike("telephone", "%"+(String) searchMap.get("telephone")+"%");
            }
            //分页
            /*if(StringUtil.isNotEmpty((String) searchMap.get("pageNum"))){
                pageNum= Integer.parseInt((String) searchMap.get("pageNum"));
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("pageSize"))){
                pageSize= Integer.parseInt((String) searchMap.get("pageSize"));
            }*/
            if((Integer) searchMap.get("pageNum") !=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if((Integer) searchMap.get("pageSize") !=null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页条件与下面的代码不能分开和换序
        Page<Worker> workers = (Page<Worker>) workerDao.selectByExample(example);
        return workers;
    }

    @Override
    public Boolean add(Worker worker) {
        int row = workerDao.insert(worker);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Worker findById(Integer id) {

        return workerDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Worker worker) {
        int row = workerDao.updateByPrimaryKeySelective(worker);
        if(row>0){
            return true;
        }

        else {
            return  false;
        }
    }



    @Override
    public Boolean del(List<Integer> ids) {
        for (Integer id:ids){
            workerDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

