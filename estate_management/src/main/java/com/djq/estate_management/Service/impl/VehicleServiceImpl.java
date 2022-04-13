package com.djq.estate_management.Service.impl;

import com.djq.estate_management.Dao.VehicleDao;
import com.djq.estate_management.Domain.Vehicle;
import com.djq.estate_management.Service.VehicleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = vehicleDao.selectAll();
        return vehicles;
    }

    @Override
    public Page<Vehicle> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Vehicle.class);//指定查询的表tb_car
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
        Page<Vehicle> vehicles = (Page<Vehicle>) vehicleDao.selectByExample(example);
        return vehicles;
    }

    @Override
    public Boolean add(Vehicle vehicle) {
        int row = vehicleDao.insert(vehicle);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Vehicle findById(Integer id) {

        return vehicleDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Vehicle vehicle) {
        int row = vehicleDao.updateByPrimaryKeySelective(vehicle);
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
            vehicleDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

