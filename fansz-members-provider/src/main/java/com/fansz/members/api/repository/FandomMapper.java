package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.model.fandom.FandomInfoResult;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface FandomMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FandomEntity record);

    int insertSelective(FandomEntity record);

    FandomEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FandomEntity record);

    int updateByPrimaryKey(FandomEntity record);

    List<FandomEntity> selectByParentId(Long id);

    List<FandomInfoResult> listByCondition(FandomEntity param);
}