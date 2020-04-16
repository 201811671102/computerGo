package com.computerGo.service.impl;

import com.computerGo.mapper.URMapper;
import com.computerGo.pojo.UR;
import com.computerGo.pojo.URExample;
import com.computerGo.service.URService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName URServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:45
 **/
@Service
public class URServiceImpl implements URService {
    @Autowired
    private URMapper urMapper;
    @Override
    public int insertUR(UR ur) throws Exception {
        return urMapper.insertSelective(ur);
    }

    @Override
    public int deleteByRid(Integer rid) throws Exception {
        URExample urExample = new URExample();
        URExample.Criteria criteria = urExample.createCriteria();
        criteria.andRidEqualTo(rid);
        return urMapper.deleteByExample(urExample);
    }

    @Override
    public List<UR> selectByUid(Integer uid, int offset, int limit)throws Exception {
        URExample urExample = new URExample();
        URExample.Criteria criteria = urExample.createCriteria();
        criteria.andUidEqualTo(uid);
        RowBounds rowBounds = new RowBounds(offset,limit);
        return urMapper.selectByExampleWithRowbounds(urExample,rowBounds);
    }

    @Override
    public UR selectByRid(Integer rid) throws Exception{
        URExample urExample = new URExample();
        URExample.Criteria criteria = urExample.createCriteria();
        criteria.andRidEqualTo(rid);
        List<UR> urList = urMapper.selectByExample(urExample);
        if(urList.isEmpty() || urList.size() == 0){
            return null;
        }
        return urList.get(0);
    }
}
