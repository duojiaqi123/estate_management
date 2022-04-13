package com.djq.estate_management.Service.impl;


import com.djq.estate_management.Dao.HomeDao;
import com.djq.estate_management.Domain.Home;
import com.djq.estate_management.Service.HomeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeDao homeDao;

    @Override
    public List<Home> findAll() {
        List<Home> homes = homeDao.selectAll();
        return homes;
    }

    @Override
    public Page<Home> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Home.class);//指定查询的表tb_house
        //初始化分页条件
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap !=null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件
            //时间区间
            if(StringUtil.isNotEmpty((String) searchMap.get("startTime"))){
                criteria.andGreaterThanOrEqualTo("liveTime",searchMap.get("startTime"));
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("endTime"))){
                criteria.andLessThanOrEqualTo("liveTime",searchMap.get("endTime"));
            }
            //名称模糊查询
            if(StringUtil.isNotEmpty((String) searchMap.get("name"))){
                criteria.andLike("name", "%"+(String) searchMap.get("name")+"%");
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
        Page<Home> homes = (Page<Home>) homeDao.selectByExample(example);
        return homes;
    }

    @Override
    public Boolean add(Home home) {
        int row = homeDao.insert(home);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Home findById(Integer id) {

        return homeDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Home home) {
        int row = homeDao.updateByPrimaryKeySelective(home);
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
            homeDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

