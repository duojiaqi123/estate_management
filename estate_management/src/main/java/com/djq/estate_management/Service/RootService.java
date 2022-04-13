package com.djq.estate_management.Service;


import com.djq.estate_management.Domain.Root;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface RootService {

    public List<Root> findAll();
    public Page<Root>  search(Map searchMap);

    public  Boolean add(Root root);

    public Root findById(Integer id);

    public Boolean update(Root root);


    public Boolean del(List<Integer> ids);
}
