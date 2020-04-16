package com.computerGo.service;

import com.computerGo.pojo.Theorder;

public interface TheorderService {
    /*添加订单*/
    int insertOrder(Theorder order)throws Exception;
    /*删除订单*/
    int deleteOrder(Integer oid)throws Exception;
    /*查询订单*/
    Theorder selectByOid(Integer oid)throws Exception;
    /*修改订单状态*/
    int updateOrderState(Theorder order)throws Exception;
    /*uid查询订单*/
}
