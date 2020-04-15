package com.computerGo.service.impl;

import com.computerGo.mapper.IdentityMapper;
import com.computerGo.pojo.Identity;
import com.computerGo.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName IdentityServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:43
 **/
@Service
public class IdentityServiceImpl implements IdentityService {
    @Autowired
    private IdentityMapper identityMapper;
    @Override
    public Identity selectByIid(Integer iid) throws Exception{
        return identityMapper.selectByPrimaryKey(iid);
    }

    @Override
    public int deleteByIid(Integer iid) throws Exception{
        return identityMapper.deleteByPrimaryKey(iid);
    }

    @Override
    public int insertIdentity(Identity identity) throws Exception {
        return identityMapper.insertSelective(identity);
    }
}
