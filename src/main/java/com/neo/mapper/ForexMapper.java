package com.neo.mapper;

import com.neo.model.Forex;


public interface ForexMapper {

    void insertForex(Forex forex);

    Forex getSelectForex(String StartDate,String EndDate,String Currency);
}
