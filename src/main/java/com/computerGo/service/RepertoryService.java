package com.computerGo.service;

import com.computerGo.pojo.Repertory;

import java.util.List;

public interface RepertoryService {
    /*rid查询商品*/
    Repertory selectByRid(Integer rid)throws Exception;
    /*商品名查询商品*/
    List<Repertory> selectByTitleType(String title,Integer type,int offset, int limit)throws Exception;
    /*rid删除商品*/
    int deleteByRid(Integer rid)throws Exception;
    /*rid修改商品*/
    int changeByRid(Repertory repertory)throws Exception;
    /*增加商品*/
    int insertRepertory(Repertory repertory)throws Exception;
    /*更新商品数量*/
    int updateRepertory(Repertory repertory)throws Exception;
    /*根据状态查询*/
    List<Repertory> selectByType(Integer type,Integer offset,Integer limit)throws Exception;
}
