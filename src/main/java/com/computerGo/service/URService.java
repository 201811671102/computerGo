package com.computerGo.service;


import com.computerGo.pojo.UR;

import java.util.List;

public interface URService {
    /*添加商品库存*/
    int insertUR(UR ur)throws Exception;
    /*rid删除商品库存*/
    int deleteByRid(Integer rid)throws Exception;
   /*uid查询商品库存*/
    List<UR> selectByUid(Integer uid,int offset,int limit);

}
