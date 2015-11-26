package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomPostEntity;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface FandomPostEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FandomPostEntity record);

    int insertSelective(FandomPostEntity record);

    FandomPostEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FandomPostEntity record);

    int updateByPrimaryKey(FandomPostEntity record);
}