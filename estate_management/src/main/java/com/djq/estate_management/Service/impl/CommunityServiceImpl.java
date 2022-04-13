package com.djq.estate_management.Service.impl;

import com.djq.estate_management.Dao.CommunityDao;
import com.djq.estate_management.Domain.Community;
import com.djq.estate_management.Service.CommunityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityDao communityDao;

    @Override
    public List<Community> findAll() {
        List<Community> communities = communityDao.selectAll();
        return communities;
    }

    @Override
    public Page<Community> search(Map searchMap) {
        //通用Mapper多条件搜索的标准写法
        Example example = new Example(Community.class);//指定查询的表tb_community
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
        Page<Community> communities = (Page<Community>) communityDao.selectByExample(example);
        return communities;
    }

    @Override
    public Boolean add(Community community) {
        int row = communityDao.insert(community);
        if(row>0){
          return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Community findById(Integer id) {
        return communityDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Community community) {
        int row = communityDao.updateByPrimaryKeySelective(community);
        if(row>0){
            return true;
        }

        else {
            return  false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Community community=new Community();
        community.setId(id);
        community.setStatus(status);
        int row = communityDao.updateByPrimaryKeySelective(community);
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
        communityDao.deleteByPrimaryKey(id);
        }
        return true;
    }


}

