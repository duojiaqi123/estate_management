package com.djq.estate_management.Service.impl;

import com.djq.estate_management.Dao.BuildingDao;
import com.djq.estate_management.Domain.Building;
import com.djq.estate_management.Service.BuildingService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingDao buildingDao;

    @Override
    public List<Building> findAll() {
        List<Building> buildings = buildingDao.selectAll();
        return buildings;
    }

    @Override
    public Page<Building> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Building.class);//指定查询的表tb_building
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

            if((Integer) searchMap.get("pageNum") !=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if((Integer) searchMap.get("pageSize") !=null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页条件与下面的代码不能分开和换序
        Page<Building> buildings = (Page<Building>) buildingDao.selectByExample(example);
        return buildings;
    }

    @Override
    public Boolean add(Building building) {
        int row = buildingDao.insert(building);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Building findById(Integer id) {

        return buildingDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Building building) {
        int row = buildingDao.updateByPrimaryKeySelective(building);
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
            buildingDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

