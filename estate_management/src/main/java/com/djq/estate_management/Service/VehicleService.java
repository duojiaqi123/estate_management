package com.djq.estate_management.Service;

import com.djq.estate_management.Domain.Vehicle;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface VehicleService {

    public List<Vehicle> findAll();
    public Page<Vehicle>  search(Map searchMap);
    public  Boolean add(Vehicle vehicle);

    public Vehicle findById(Integer id);

    public Boolean update(Vehicle vehicle);


    public Boolean del(List<Integer> ids);
}
