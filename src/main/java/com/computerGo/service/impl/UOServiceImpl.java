package com.computerGo.service.impl;

import com.computerGo.mapper.UOMapper;
import com.computerGo.pojo.UO;
import com.computerGo.pojo.UOExample;
import com.computerGo.service.UOService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UOServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:46
 **/
@Service
public class UOServiceImpl implements UOService {
    @Autowired
    private UOMapper uoMapper;
    @Override
    public int insertUO(UO uo) throws Exception {
        return uoMapper.insertSelective(uo);
    }

    @Override
    public int deleteByoid(Integer oid) throws Exception {
        UOExample uoExample = new UOExample();
        UOExample.Criteria criteria = uoExample.createCriteria();
        criteria.andOidEqualTo(oid);
        return uoMapper.deleteByExample(uoExample);
    }

    @Override
    public List<UO> selectByOid(Integer oid, int offset, int limit) throws Exception {
        UOExample uoExample = new UOExample();
        UOExample.Criteria criteria = uoExample.createCriteria();
        criteria.andOidEqualTo(oid);
        RowBounds rowBounds = new RowBounds(offset,limit);
        return uoMapper.selectByExampleWithRowbounds(uoExample,rowBounds);
    }

    @Override
    public List<UO> selectByUid(Integer uid, int offset, int limit) throws Exception {
        UOExample uoExample = new UOExample();
        UOExample.Criteria criteria = uoExample.createCriteria();
        criteria.andUidEqualTo(uid);
        RowBounds rowBounds = new RowBounds(offset,limit);
        return uoMapper.selectByExampleWithRowbounds(uoExample,rowBounds);
    }
}
