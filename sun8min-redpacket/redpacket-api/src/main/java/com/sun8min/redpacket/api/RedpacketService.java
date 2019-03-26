package com.sun8min.redpacket.api;

import com.sun8min.redpacket.entity.Redpacket;

import java.util.List;

public interface RedpacketService {
    int deleteByPrimaryKey(Long redpacketId);

    int insert(Redpacket record);

    Redpacket selectByPrimaryKey(Long redpacketId);

    List<Redpacket> selectAll();

    int updateByPrimaryKey(Redpacket record);
}