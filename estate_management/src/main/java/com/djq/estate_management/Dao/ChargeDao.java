package com.djq.estate_management.Dao;

import com.djq.estate_management.Domain.Charge;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChargeDao extends Mapper<Charge> {

}
