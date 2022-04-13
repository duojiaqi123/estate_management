package com.djq.estate_management.Service;

import com.djq.estate_management.Domain.Activity;
import com.djq.estate_management.Domain.Repair;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface RepairService {

    public List<Repair> findAll();
    public Page<Repair>  search(Map searchMap);
    public  Boolean add(Repair repair);

    public Repair findById(Integer id);

    public Boolean update(Repair repair);

    public Boolean updateStatus(String status, Integer id);

    public Boolean del(List<Integer> ids);
}
