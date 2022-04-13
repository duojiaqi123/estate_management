package com.djq.estate_management.Dao;


import com.djq.estate_management.Domain.Worker;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface WorkerDao extends Mapper<Worker> {
    @Select("select * from tb_worker where name = #{name} and password = #{password} and telephone = #{telephone}")
    public Worker selectByNameAndPasswordAndTelephone(String name,String password,String telephone);
}
