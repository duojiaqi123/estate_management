package com.djq.estate_management.Service;

import com.djq.estate_management.Domain.TradingStream;

import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TradingStreamService {

    public List<TradingStream> findAll();
    public Page<TradingStream>  search(Map searchMap);
    public  Boolean add(TradingStream tradingStream);

    public TradingStream findById(Integer id);

    public Boolean update(TradingStream tradingStream);

    public Boolean del(List<Integer> ids);
}
