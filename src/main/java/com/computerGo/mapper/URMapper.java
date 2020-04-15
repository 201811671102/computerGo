package com.computerGo.mapper;

import com.computerGo.pojo.UR;
import com.computerGo.pojo.URExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
public interface URMapper {
    long countByExample(URExample example);

    int deleteByExample(URExample example);

    int deleteByPrimaryKey(Integer urid);

    int insert(UR record);

    int insertSelective(UR record);

    List<UR> selectByExampleWithRowbounds(URExample example, RowBounds rowBounds);

    List<UR> selectByExample(URExample example);

    UR selectByPrimaryKey(Integer urid);

    int updateByExampleSelective(@Param("record") UR record, @Param("example") URExample example);

    int updateByExample(@Param("record") UR record, @Param("example") URExample example);

    int updateByPrimaryKeySelective(UR record);

    int updateByPrimaryKey(UR record);
}