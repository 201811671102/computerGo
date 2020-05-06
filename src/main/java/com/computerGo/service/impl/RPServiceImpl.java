package com.computerGo.service.impl;

import com.computerGo.mapper.RPMapper;
import com.computerGo.pojo.RP;
import com.computerGo.pojo.RPExample;
import com.computerGo.service.RPService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RPServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:45
 **/
@Service
public class RPServiceImpl implements RPService {
    @Autowired
    private RPMapper rpMapper;
    @Override
    public int insertRP(RP rp) throws Exception {
        return rpMapper.insertSelective(rp);
    }

    @Override
    public int deleteByRid(Integer rid) throws Exception {
        RPExample rpExample = new RPExample();
        RPExample.Criteria criteria = rpExample.createCriteria();
        criteria.andRidEqualTo(rid);
        return rpMapper.deleteByExample(rpExample);
    }

    @Override
    public int deleteByPid(Integer pid) throws Exception {
        RPExample rpExample = new RPExample();
        RPExample.Criteria criteria = rpExample.createCriteria();
        criteria.andPidEqualTo(pid);
        return rpMapper.deleteByExample(rpExample);
    }

    @Override
    public List<RP> selectByRid(Integer rid) throws Exception {
        RPExample rpExample = new RPExample();
        RPExample.Criteria criteria = rpExample.createCriteria();
        criteria.andRidEqualTo(rid);
        return rpMapper.selectByExample(rpExample);
    }


    @Override
    public RP selectBypid(Integer pid) throws Exception {
        RPExample rpExample = new RPExample();
        RPExample.Criteria criteria = rpExample.createCriteria();
        criteria.andPidEqualTo(pid);
        List<RP> rpList = rpMapper.selectByExample(rpExample);
        if (rpList.isEmpty() || rpList.size() == 0){
            return null;
        }
        return rpList.get(0);
    }
}
