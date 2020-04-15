package com.computerGo.service;

import com.computerGo.pojo.RP;

import java.util.List;

public interface RPService {
    /*增加库存套餐记录*/
    int insertRP(RP rp)throws Exception;
    /*rid删除库存套餐记录*/
    int deleteByRid(Integer rid)throws Exception;
    /*pid删除库存套餐记录*/
    int deleteByPid(Integer pid)throws Exception;
    /*rid查询记录*/
    List<RP> selectByRid(Integer rid)throws Exception;
    /*type查询记录*/
    List<RP> selectByType(Integer type,int offset, int limit)throws Exception;
}
