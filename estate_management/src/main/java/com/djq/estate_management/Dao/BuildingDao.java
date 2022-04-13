package com.djq.estate_management.Dao;

import com.djq.estate_management.Domain.Building;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BuildingDao extends Mapper<Building> {

}
