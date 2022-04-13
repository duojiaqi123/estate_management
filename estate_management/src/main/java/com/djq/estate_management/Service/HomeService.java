package com.djq.estate_management.Service;


import com.djq.estate_management.Domain.Home;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface HomeService {

    public List<Home> findAll();
    public Page<Home>  search(Map searchMap);
    public  Boolean add(Home home);

    public Home findById(Integer id);

    public Boolean update(Home home);


    public Boolean del(List<Integer> ids);
}
