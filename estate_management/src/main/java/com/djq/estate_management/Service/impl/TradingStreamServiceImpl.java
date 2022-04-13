package com.djq.estate_management.Service.impl;

import com.djq.estate_management.Dao.TradingStreamDao;
import com.djq.estate_management.Domain.Community;
import com.djq.estate_management.Domain.TradingStream;

import com.djq.estate_management.Service.TradingStreamService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class TradingStreamServiceImpl implements TradingStreamService {
    @Autowired
    private TradingStreamDao tradingStreamDao;

    @Override
    public List<TradingStream> findAll() {
        List<TradingStream> tradingStreams = tradingStreamDao.selectAll();
        return tradingStreams;
    }

    @Override
    public Page<TradingStream> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(TradingStream.class);//指定查询的表tb_tradingStream
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
        Page<TradingStream> tradingStreams = (Page<TradingStream>) tradingStreamDao.selectByExample(example);
        return tradingStreams;
    }

    @Override
    public Boolean add(TradingStream tradingStream) {
        int row = tradingStreamDao.insert(tradingStream);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public TradingStream findById(Integer id) {
        return tradingStreamDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(TradingStream tradingStream) {
        int row = tradingStreamDao.updateByPrimaryKeySelective(tradingStream);
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
            tradingStreamDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

