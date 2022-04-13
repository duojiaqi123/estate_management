package com.djq.estate_management.Service;


import com.djq.estate_management.Domain.Personnel;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface PersonnelService {

    public List<Personnel> findAll();
    public Page<Personnel>  search(Map searchMap);

    public  Boolean add(Personnel personnel);

    public Personnel findById(Integer id);

    public Boolean update(Personnel personnel);


    public Boolean del(List<Integer> ids);
}
