package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomPostEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import java.util.List;

@MapperScan
@Service
public interface FandomPostEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FandomPostEntity record);

    int insertSelective(FandomPostEntity record);

    FandomPostEntity selectByPrimaryKey(Integer id);

    List<FandomPostEntity> selectNewByFandomId(Integer id);

    List<FandomPostEntity> selectHotByFandomId(Integer id);

    int updateByPrimaryKeySelective(FandomPostEntity record);

    int updateByPrimaryKey(FandomPostEntity record);
}