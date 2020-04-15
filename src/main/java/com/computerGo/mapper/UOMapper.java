package com.computerGo.mapper;

import com.computerGo.pojo.UO;
import com.computerGo.pojo.UOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
public interface UOMapper {
    long countByExample(UOExample example);

    int deleteByExample(UOExample example);

    int deleteByPrimaryKey(Integer uoid);

    int insert(UO record);

    int insertSelective(UO record);

    List<UO> selectByExampleWithRowbounds(UOExample example, RowBounds rowBounds);

    List<UO> selectByExample(UOExample example);

    UO selectByPrimaryKey(Integer uoid);

    int updateByExampleSelective(@Param("record") UO record, @Param("example") UOExample example);

    int updateByExample(@Param("record") UO record, @Param("example") UOExample example);

    int updateByPrimaryKeySelective(UO record);

    int updateByPrimaryKey(UO record);
}