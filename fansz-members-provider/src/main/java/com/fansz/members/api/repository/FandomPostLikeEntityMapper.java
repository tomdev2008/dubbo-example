package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomPostLikeEntity;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface FandomPostLikeEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FandomPostLikeEntity record);

    int insertSelective(FandomPostLikeEntity record);

    FandomPostLikeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FandomPostLikeEntity record);

    int updateByPrimaryKey(FandomPostLikeEntity record);
}