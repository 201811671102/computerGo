package com.computerGo.service;

import com.computerGo.pojo.Order;

public interface OrderService {
    /*添加订单*/
    int insertOrder(Order order)throws Exception;
    /*删除订单*/
    int deleteOrder(Integer oid)throws Exception;
    /*查询订单*/
    Order selectByOid(Integer oid)throws Exception;
    /*修改订单状态*/
    int updateOrderState(Order order)throws Exception;
}
