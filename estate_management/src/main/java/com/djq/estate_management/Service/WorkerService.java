package com.djq.estate_management.Service;



import com.djq.estate_management.Domain.Worker;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface WorkerService {

    public List<Worker> findAll();
    public Page<Worker>  search(Map searchMap);

    public  Boolean add(Worker worker);

    public Worker findById(Integer id);

    public Boolean update(Worker worker);


    public Boolean del(List<Integer> ids);
}
