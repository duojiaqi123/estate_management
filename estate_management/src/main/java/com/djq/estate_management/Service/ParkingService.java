package com.djq.estate_management.Service;

import com.djq.estate_management.Domain.Parking;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ParkingService {

    public List<Parking> findAll();
    public Page<Parking>  search(Map searchMap);
    public  Boolean add(Parking parking);

    public Parking findById(Integer id);

    public Boolean update(Parking parking);

    public Boolean updateStatus(String status, Integer id);

    public Boolean del(List<Integer> ids);
}
