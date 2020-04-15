package com.computerGo.service.impl;

import com.computerGo.mapper.UserMapper;
import com.computerGo.pojo.User;
import com.computerGo.pojo.UserExample;
import com.computerGo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:43
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByOpenid(String openid) throws Exception {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOpenidEqualTo(openid);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty() || users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    @Override
    public User selectByUid(Integer uid) throws Exception {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public int insertUser(User user) throws Exception {
        return userMapper.insertSelective(user);
    }
}
