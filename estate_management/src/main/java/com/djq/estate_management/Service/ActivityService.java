package com.djq.estate_management.Service;

import com.djq.estate_management.Domain.Activity;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    public List<Activity> findAll();
    public Page<Activity>  search(Map searchMap);
    public  Boolean add(Activity activity);

    public Activity findById(Integer id);

    public Boolean update(Activity activity);

    public Boolean updateStatus(String status, Integer id);

    public Boolean del(List<Integer> ids);
}
