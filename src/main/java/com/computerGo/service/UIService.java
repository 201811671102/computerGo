package com.computerGo.service;

import com.computerGo.pojo.UI;

import java.util.List;

public interface UIService {
    /*根据uid查询*/
    List<UI> selectByUid(Integer uid)throws Exception;
    /*插入ui记录*/
    int insertUI(UI ui) throws Exception;
    /*iid删除记录*/
    int deleteByIid(Integer iid)throws Exception;
}
