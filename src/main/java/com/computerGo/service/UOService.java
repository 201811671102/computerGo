package com.computerGo.service;

import com.computerGo.pojo.UO;

import java.util.List;

public interface UOService {
    /*新增用户订单记录*/
    int insertUO(UO uo)throws Exception;
    /*oid删除用户订单记录*/
    int deleteByoid(Integer oid)throws Exception;
    /*oid查询用户订单记录*/
    List<UO> selectByOid(Integer oid,int offset,int limit)throws Exception;
    /*uid查询用户订单记录*/
    List<UO> selectByUid(Integer uid,int offset,int limit)throws Exception;
    /*oid查询购买人数*/
    long getCount(Integer oid)throws Exception;

}
