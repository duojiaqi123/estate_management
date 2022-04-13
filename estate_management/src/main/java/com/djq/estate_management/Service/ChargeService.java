package com.djq.estate_management.Service;

import com.djq.estate_management.Domain.Charge;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ChargeService {

    public List<Charge> findAll();
    public Page<Charge>  search(Map searchMap);
    public  Boolean add(Charge charge);

    public Charge findById(Integer id);

    public Boolean update(Charge charge);

    public Boolean updateStatus(String status, Integer id);

    public Boolean del(List<Integer> ids);
}
