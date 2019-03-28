package com.sun8min.capital.provider;

import com.sun8min.capital.api.CapitalService;
import com.sun8min.capital.dao.CapitalDao;
import com.sun8min.capital.entity.Capital;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@Service(version = "${service.version}")
public class CapitalServiceImpl implements CapitalService {

    @Autowired
    CapitalDao capitalDao;

    public int deleteByPrimaryKey(Long capitalId) {
        return capitalDao.deleteByPrimaryKey(capitalId);
    }

    public int insert(Capital record) {
        return capitalDao.insert(record);
    }

    public Capital selectByPrimaryKey(Long capitalId) {
        return capitalDao.selectByPrimaryKey(capitalId);
    }

    public List<Capital> selectAll() {
        return capitalDao.selectAll();
    }

    public int updateByPrimaryKey(Capital record) {
        return capitalDao.updateByPrimaryKey(record);
    }

    public BigDecimal findAmountByUserId(Long userId) {
        return capitalDao.findByUserId(userId).getCapitalAmount();
    }
}