package com.computerGo.service.impl;

import com.computerGo.mapper.RepertoryMapper;
import com.computerGo.pojo.Repertory;
import com.computerGo.pojo.RepertoryExample;
import com.computerGo.service.RepertoryService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RepertoryServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:44
 **/
@Service
public class RepertoryServiceImpl implements RepertoryService {
    @Autowired
    private RepertoryMapper repertoryMapper;
    @Override
    public Repertory selectByRid(Integer rid)throws Exception {
        return repertoryMapper.selectByPrimaryKey(rid);
    }

    @Override
    public List<Repertory> selectByTitle(String title,int offset, int limit)throws Exception {
        RepertoryExample repertoryExample = new RepertoryExample();
        RepertoryExample.Criteria criteria = repertoryExample.createCriteria();
        criteria.andTitleLike("%"+title+"%");
        RowBounds rowBounds = new RowBounds(offset,limit);
        return repertoryMapper.selectByExampleWithRowbounds(repertoryExample,rowBounds);
    }

    @Override
    public int deleteByRid(Integer rid) throws Exception{
        return repertoryMapper.deleteByPrimaryKey(rid);
    }

    @Override
    public int changeByRid(Repertory repertory)throws Exception {
        return repertoryMapper.updateByPrimaryKeySelective(repertory);
    }

    @Override
    public int insertRepertory(Repertory repertory)throws Exception {
        return repertoryMapper.insertSelective(repertory);
    }

    @Override
    public int updateRepertory(Repertory repertory) throws Exception {
        return repertoryMapper.updateByPrimaryKey(repertory);
    }

    @Override
    public List<Repertory> selectByType(Integer type,Integer offset,Integer limit) throws Exception {
       RepertoryExample repertoryExample = new RepertoryExample();
       RepertoryExample.Criteria criteria = repertoryExample.createCriteria();
       criteria.andTypeEqualTo(type);
       if (offset != -1 && limit != -1){
           RowBounds rowBounds = new RowBounds(offset,limit);
           return repertoryMapper.selectByExampleWithRowbounds(repertoryExample,rowBounds);
       }
      return repertoryMapper.selectByExample(repertoryExample);
    }
}
