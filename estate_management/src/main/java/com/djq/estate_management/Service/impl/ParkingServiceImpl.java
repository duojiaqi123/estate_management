package com.djq.estate_management.Service.impl;

import com.djq.estate_management.Dao.ParkingDao;
import com.djq.estate_management.Domain.Parking;
import com.djq.estate_management.Service.ParkingService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ParkingServiceImpl implements ParkingService {
    @Autowired
    private ParkingDao parkingDao;

    @Override
    public List<Parking> findAll() {
        List<Parking> parkings = parkingDao.selectAll();
        return parkings;
    }

    @Override
    public Page<Parking> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Parking.class);//指定查询的表tb_parking
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
            if(StringUtil.isNotEmpty((String) searchMap.get("code"))){
                criteria.andLike("code", "%"+(String) searchMap.get("code")+"%");
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
        Page<Parking> parkings = (Page<Parking>) parkingDao.selectByExample(example);
        return parkings;
    }

    @Override
    public Boolean add(Parking parking) {
        int row = parkingDao.insert(parking);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Parking findById(Integer id) {
        return parkingDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Parking parking) {
        int row = parkingDao.updateByPrimaryKeySelective(parking);
        if(row>0){
            return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Parking parking=new Parking();
        parking.setId(id);
        parking.setStatus(status);
        int row = parkingDao.updateByPrimaryKeySelective(parking);
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
        parkingDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

