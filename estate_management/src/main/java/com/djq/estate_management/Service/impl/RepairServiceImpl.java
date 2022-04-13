package com.djq.estate_management.Service.impl;


import com.djq.estate_management.Dao.RepairDao;
import com.djq.estate_management.Domain.Repair;
import com.djq.estate_management.Service.RepairService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairDao repairDao;

    @Override
    public List<Repair> findAll() {
        List<Repair> repairs = repairDao.selectAll();
        return repairs;
    }

    @Override
    public Page<Repair> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Repair.class);//指定查询的表tb_repair
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
            if(StringUtil.isNotEmpty((String) searchMap.get("ownerName"))){
                criteria.andLike("ownerName", "%"+(String) searchMap.get("ownerName")+"%");
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("telephone"))){
                criteria.andLike("telephone", "%"+(String) searchMap.get("telephone")+"%");
            }
            //分页

            if((Integer) searchMap.get("pageNum") !=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if((Integer) searchMap.get("pageSize") !=null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页条件与下面的代码不能分开和换序
        Page<Repair> repairs = (Page<Repair>) repairDao.selectByExample(example);
        return repairs;
    }

    @Override
    public Boolean add(Repair repair) {
        int row = repairDao.insert(repair);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Repair findById(Integer id) {
        return repairDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Repair repair) {
        int row = repairDao.updateByPrimaryKeySelective(repair);
        if(row>0){
            return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Repair repair=new Repair();
        repair.setId(id);
        repair.setStatus(status);
        int row = repairDao.updateByPrimaryKeySelective(repair);
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
        repairDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

