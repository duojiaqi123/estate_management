package com.djq.estate_management.Dao;


import com.djq.estate_management.Domain.Personnel;
import com.djq.estate_management.Domain.Root;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface PersonnelDao extends Mapper<Personnel> {
    @Select("select * from tb_owner where name = #{name} and password = #{password} and telephone = #{telephone}")
    public Personnel selectByNameAndPasswordAndTelephone(String name,String password,String telephone);
}
