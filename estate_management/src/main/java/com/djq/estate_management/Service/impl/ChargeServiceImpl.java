package com.djq.estate_management.Service.impl;

import com.djq.estate_management.Dao.ChargeDao;
import com.djq.estate_management.Domain.Charge;
import com.djq.estate_management.Service.ChargeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ChargeServiceImpl implements ChargeService {
    @Autowired
    private ChargeDao chargeDao;

    @Override
    public List<Charge> findAll() {
        List<Charge> charges = chargeDao.selectAll();
        return charges;
    }

    @Override
    public Page<Charge> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Charge.class);//指定查询的表tb_charge
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
        Page<Charge> charges = (Page<Charge>) chargeDao.selectByExample(example);
        return charges;
    }

    @Override
    public Boolean add(Charge charge) {
        int row = chargeDao.insert(charge);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Charge findById(Integer id) {
        return chargeDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Charge charge) {
        int row = chargeDao.updateByPrimaryKeySelective(charge);
        if(row>0){
            return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Charge charge=new Charge();
        charge.setId(id);
        charge.setStatus(status);
        int row = chargeDao.updateByPrimaryKeySelective(charge);
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
        chargeDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

