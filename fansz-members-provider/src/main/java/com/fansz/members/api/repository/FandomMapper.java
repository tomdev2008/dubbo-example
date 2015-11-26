package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomEntity;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface FandomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FandomEntity record);

    int insertSelective(FandomEntity record);

    FandomEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FandomEntity record);

    int updateByPrimaryKey(FandomEntity record);
}