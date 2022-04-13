package com.djq.estate_management.Dao;

import com.djq.estate_management.Domain.Vehicle;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface VehicleDao extends Mapper<Vehicle> {

}
