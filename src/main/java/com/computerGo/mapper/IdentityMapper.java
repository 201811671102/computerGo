package com.computerGo.mapper;

import com.computerGo.pojo.Identity;
import com.computerGo.pojo.IdentityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
public interface IdentityMapper {
    long countByExample(IdentityExample example);

    int deleteByExample(IdentityExample example);

    int deleteByPrimaryKey(Integer iid);

    int insert(Identity record);

    int insertSelective(Identity record);

    List<Identity> selectByExampleWithRowbounds(IdentityExample example, RowBounds rowBounds);

    List<Identity> selectByExample(IdentityExample example);

    Identity selectByPrimaryKey(Integer iid);

    int updateByExampleSelective(@Param("record") Identity record, @Param("example") IdentityExample example);

    int updateByExample(@Param("record") Identity record, @Param("example") IdentityExample example);

    int updateByPrimaryKeySelective(Identity record);

    int updateByPrimaryKey(Identity record);
}