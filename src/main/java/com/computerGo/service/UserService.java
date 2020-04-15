package com.computerGo.service;

import com.computerGo.pojo.User;

public interface UserService {
    /*根据openid查询*/
    User selectByOpenid(String openid)throws Exception;
    /*根据uid查询*/
    User selectByUid(Integer uid)throws Exception;
    /*插入user*/
    int insertUser(User user)throws Exception;
}
