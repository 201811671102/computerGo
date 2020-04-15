package com.computerGo.service.impl;

import com.computerGo.mapper.OrderMapper;
import com.computerGo.pojo.Order;
import com.computerGo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:45
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public int insertOrder(Order order) throws Exception {
        return orderMapper.insertSelective(order);
    }

    @Override
    public int deleteOrder(Integer oid) throws Exception {
        return orderMapper.deleteByPrimaryKey(oid);
    }

    @Override
    public Order selectByOid(Integer oid) throws Exception {
        return orderMapper.selectByPrimaryKey(oid);
    }

    @Override
    public int updateOrderState(Order order) throws Exception {
        return orderMapper.updateByPrimaryKeySelective(order);
    }
}
