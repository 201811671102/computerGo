package com.computerGo.service.impl;

import com.computerGo.mapper.TheorderMapper;
import com.computerGo.pojo.Theorder;
import com.computerGo.service.TheorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:45
 **/
@Service
public class TheorderServiceImpl implements TheorderService {
    @Autowired
    private TheorderMapper theorderMapper;
    @Override
    public int insertOrder(Theorder order) throws Exception {
        return theorderMapper.insertSelective(order);
    }

    @Override
    public int deleteOrder(Integer oid) throws Exception {
        return theorderMapper.deleteByPrimaryKey(oid);
    }

    @Override
    public Theorder selectByOid(Integer oid) throws Exception {
        return theorderMapper.selectByPrimaryKey(oid);
    }

    @Override
    public int updateOrderState(Theorder theorder) throws Exception {
        return theorderMapper.updateByPrimaryKeySelective(theorder);
    }
}
