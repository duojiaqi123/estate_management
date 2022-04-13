package com.djq.estate_management.Service.impl;

import com.djq.estate_management.Dao.ActivityDao;
import com.djq.estate_management.Domain.Activity;
import com.djq.estate_management.Service.ActivityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Override
    public List<Activity> findAll() {
        List<Activity> activitys = activityDao.selectAll();
        return activitys;
    }

    @Override
    public Page<Activity> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Activity.class);//指定查询的表tb_activity
        //初始化分页条件
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap !=null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件
            //时间区间
            if(StringUtil.isNotEmpty((String) searchMap.get("startTime"))){
                criteria.andGreaterThanOrEqualTo("startTime",searchMap.get("startTime"));
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("endTime"))){
                criteria.andLessThanOrEqualTo("startTime",searchMap.get("endTime"));
            }
            //名称模糊查询
            if(StringUtil.isNotEmpty((String) searchMap.get("title"))){
                criteria.andLike("title", "%"+(String) searchMap.get("title")+"%");
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
        Page<Activity> activitys = (Page<Activity>) activityDao.selectByExample(example);
        return activitys;
    }

    @Override
    public Boolean add(Activity activity) {
        int row = activityDao.insert(activity);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Activity findById(Integer id) {
        return activityDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Activity activity) {
        int row = activityDao.updateByPrimaryKeySelective(activity);
        if(row>0){
            return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Activity activity=new Activity();
        activity.setId(id);
        activity.setStatus(status);
        int row = activityDao.updateByPrimaryKeySelective(activity);
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
        activityDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

