package com.computerGo.mapper;

import com.computerGo.pojo.UI;
import com.computerGo.pojo.UIExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
public interface UIMapper {
    long countByExample(UIExample example);

    int deleteByExample(UIExample example);

    int deleteByPrimaryKey(Integer uiid);

    int insert(UI record);

    int insertSelective(UI record);

    List<UI> selectByExampleWithRowbounds(UIExample example, RowBounds rowBounds);

    List<UI> selectByExample(UIExample example);

    UI selectByPrimaryKey(Integer uiid);

    int updateByExampleSelective(@Param("record") UI record, @Param("example") UIExample example);

    int updateByExample(@Param("record") UI record, @Param("example") UIExample example);

    int updateByPrimaryKeySelective(UI record);

    int updateByPrimaryKey(UI record);
}