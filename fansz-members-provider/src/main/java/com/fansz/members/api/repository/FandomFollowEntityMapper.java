package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomFollowEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import java.util.List;

@MapperScan
@Service
public interface FandomFollowEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FandomFollowEntity record);

    int insertSelective(FandomFollowEntity record);

    FandomFollowEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FandomFollowEntity record);

    int updateByPrimaryKey(FandomFollowEntity record);

    List<FandomFollowEntity> selectBySelective(FandomFollowEntity record);
}