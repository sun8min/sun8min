package com.sun8min.capital.dao;

import com.sun8min.capital.entity.Capital;
import java.util.List;

public interface CapitalDao {
    int deleteByPrimaryKey(Long capitalId);

    int insert(Capital record);

    Capital selectByPrimaryKey(Long capitalId);

    List<Capital> selectAll();

    int updateByPrimaryKey(Capital record);
}