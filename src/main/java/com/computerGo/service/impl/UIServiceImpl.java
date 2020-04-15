package com.computerGo.service.impl;

import com.computerGo.mapper.UIMapper;
import com.computerGo.pojo.UI;
import com.computerGo.pojo.UIExample;
import com.computerGo.service.UIService;
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
    public UI selectByUid(Integer uid) throws Exception {
        UIExample uiExample = new UIExample();
        UIExample.Criteria criteria = uiExample.createCriteria();
        criteria.andUidEqualTo(uid);
        List<UI> uiList = uiMapper.selectByExample(uiExample);
        if (uiList.isEmpty() || uiList.size() == 0){
            return null;
        }
        return uiList.get(0);
    }

    @Override
    public int insertUI(UI ui) throws Exception {
        return uiMapper.insertSelective(ui);
    }
}
