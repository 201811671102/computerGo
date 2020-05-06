package com.computerGo.mapper;

import com.computerGo.pojo.Theorder;
import com.computerGo.pojo.TheorderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
public interface TheorderMapper {
    long countByExample(TheorderExample example);

    int deleteByExample(TheorderExample example);

    int deleteByPrimaryKey(Integer oid);

    int insert(Theorder record);

    int insertSelective(Theorder record);

    List<Theorder> selectByExampleWithRowbounds(TheorderExample example, RowBounds rowBounds);

    List<Theorder> selectByExample(TheorderExample example);

    Theorder selectByPrimaryKey(Integer oid);

    int updateByExampleSelective(@Param("record") Theorder record, @Param("example") TheorderExample example);

    int updateByExample(@Param("record") Theorder record, @Param("example") TheorderExample example);

    int updateByPrimaryKeySelective(Theorder record);

    int updateByPrimaryKey(Theorder record);
}