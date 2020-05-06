package com.computerGo.service.impl;

import com.computerGo.mapper.UIMapper;
import com.computerGo.pojo.UI;
import com.computerGo.pojo.UIExample;
import com.computerGo.service.UIService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UIServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:46
 **/
@Service
public class UIServiceImpl implements UIService {
    @Autowired
    private UIMapper uiMapper;

    @Override
    public List<UI> selectByUid(Integer uid) throws Exception {
        UIExample uiExample = new UIExample();
        UIExample.Criteria criteria = uiExample.createCriteria();
        criteria.andUidEqualTo(uid);
        return uiMapper.selectByExample(uiExample);
    }

    @Override
    public int insertUI(UI ui) throws Exception {
        return uiMapper.insertSelective(ui);
    }

    @Override
    public int deleteByIid(Integer iid) throws Exception {
        UIExample uiExample = new UIExample();
        UIExample.Criteria criteria = uiExample.createCriteria();
        criteria.andIidEqualTo(iid);
        return uiMapper.deleteByExample(uiExample);
    }

    @Override
    public List<UI> selectByUidRow(Integer uid, Integer offset, Integer limit) throws Exception {
        UIExample uiExample = new UIExample();
        UIExample.Criteria criteria = uiExample.createCriteria();
        criteria.andUidEqualTo(uid);
        if (offset!=-1&&limit!=-1) {
            RowBounds rowBounds = new RowBounds(offset, limit);
            return uiMapper.selectByExampleWithRowbounds(uiExample, rowBounds);
        }
        return uiMapper.selectByExample(uiExample);
    }
}
