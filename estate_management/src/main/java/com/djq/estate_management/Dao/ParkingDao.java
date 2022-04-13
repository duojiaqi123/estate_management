package com.djq.estate_management.Dao;

import com.djq.estate_management.Domain.Building;
import com.djq.estate_management.Domain.Parking;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ParkingDao extends Mapper<Parking> {

}
