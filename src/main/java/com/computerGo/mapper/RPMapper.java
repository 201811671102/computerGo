package com.computerGo.mapper;

import com.computerGo.pojo.RP;
import com.computerGo.pojo.RPExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
public interface RPMapper {
    long countByExample(RPExample example);

    int deleteByExample(RPExample example);

    int deleteByPrimaryKey(Integer rpid);

    int insert(RP record);

    int insertSelective(RP record);

    List<RP> selectByExampleWithRowbounds(RPExample example, RowBounds rowBounds);

    List<RP> selectByExample(RPExample example);

    RP selectByPrimaryKey(Integer rpid);

    int updateByExampleSelective(@Param("record") RP record, @Param("example") RPExample example);

    int updateByExample(@Param("record") RP record, @Param("example") RPExample example);

    int updateByPrimaryKeySelective(RP record);

    int updateByPrimaryKey(RP record);
}