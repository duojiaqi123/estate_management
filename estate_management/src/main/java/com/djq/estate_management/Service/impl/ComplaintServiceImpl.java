package com.djq.estate_management.Service.impl;


import com.djq.estate_management.Dao.ComplaintDao;
import com.djq.estate_management.Domain.Complaint;
import com.djq.estate_management.Service.ComplaintService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintDao complaintDao;

    @Override
    public List<Complaint> findAll() {
        List<Complaint> complaints = complaintDao.selectAll();
        return complaints;
    }

    @Override
    public Page<Complaint> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Complaint.class);//指定查询的表tb_complaint
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
        Page<Complaint> complaints = (Page<Complaint>) complaintDao.selectByExample(example);
        return complaints;
    }

    @Override
    public Boolean add(Complaint complaint) {
        int row = complaintDao.insert(complaint);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Complaint findById(Integer id) {
        return complaintDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Complaint complaint) {
        int row = complaintDao.updateByPrimaryKeySelective(complaint);
        if(row>0){
            return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Complaint complaint=new Complaint();
        complaint.setId(id);
        complaint.setStatus(status);
        int row = complaintDao.updateByPrimaryKeySelective(complaint);
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
        complaintDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

