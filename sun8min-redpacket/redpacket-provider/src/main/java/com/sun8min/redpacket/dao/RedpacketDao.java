package com.sun8min.redpacket.dao;

import com.sun8min.redpacket.entity.Redpacket;
import java.util.List;

public interface RedpacketDao {
    int deleteByPrimaryKey(Long redpacketId);

    int insert(Redpacket record);

    Redpacket selectByPrimaryKey(Long redpacketId);

    List<Redpacket> selectAll();

    int updateByPrimaryKey(Redpacket record);
}