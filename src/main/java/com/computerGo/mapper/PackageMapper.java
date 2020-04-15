package com.computerGo.mapper;

import com.computerGo.pojo.Package;
import com.computerGo.pojo.PackageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
public interface PackageMapper {
    long countByExample(PackageExample example);

    int deleteByExample(PackageExample example);

    int deleteByPrimaryKey(Integer pid);

    int insert(Package record);

    int insertSelective(Package record);

    List<Package> selectByExampleWithRowbounds(PackageExample example, RowBounds rowBounds);

    List<Package> selectByExample(PackageExample example);

    Package selectByPrimaryKey(Integer pid);

    int updateByExampleSelective(@Param("record") Package record, @Param("example") PackageExample example);

    int updateByExample(@Param("record") Package record, @Param("example") PackageExample example);

    int updateByPrimaryKeySelective(Package record);

    int updateByPrimaryKey(Package record);
}