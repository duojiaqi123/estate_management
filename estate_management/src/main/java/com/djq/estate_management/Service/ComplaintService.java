package com.djq.estate_management.Service;

import com.djq.estate_management.Domain.Complaint;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ComplaintService {

    public List<Complaint> findAll();
    public Page<Complaint>  search(Map searchMap);
    public  Boolean add(Complaint complaint);

    public Complaint findById(Integer id);

    public Boolean update(Complaint complaint);

    public Boolean updateStatus(String status, Integer id);

    public Boolean del(List<Integer> ids);
}
