package com.djq.estate_management.Dao;


import com.djq.estate_management.Domain.Admin;
import com.djq.estate_management.Domain.Personnel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AdminDao extends Mapper<Admin> {
    @Select("select * from tb_admin where name = #{name} and password = #{password } and telephone = #{telephone }")
    public Admin selectByNameAndPasswordAndTelephone(String name,String password,String telephone);
}
