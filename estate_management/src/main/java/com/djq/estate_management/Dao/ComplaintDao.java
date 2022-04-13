package com.djq.estate_management.Dao;

import com.djq.estate_management.Domain.Complaint;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ComplaintDao extends Mapper<Complaint> {

}
