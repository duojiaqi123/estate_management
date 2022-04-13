package com.djq.estate_management.Dao;

import com.djq.estate_management.Domain.Root;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RootDao extends Mapper<Root> {

@Select("select * from tb_root where name = #{name} and password = #{password} and telephone = #{telephone}")
    public Root selectByNameAndPasswordAndTelephone(String name,String password,String telephone);

}
