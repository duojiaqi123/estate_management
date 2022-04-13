package com.djq.estate_management.Service;


import com.djq.estate_management.Domain.Admin;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface AdminService {

    public List<Admin> findAll();
    public Page<Admin>  search(Map searchMap);

    public  Boolean add(Admin admin);

    public Admin findById(Integer id);

    public Boolean update(Admin admin);

    public Boolean del(List<Integer> ids);
}
