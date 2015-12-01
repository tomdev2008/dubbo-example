package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomMemberEntity;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface FandomMemberEntityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FandomMemberEntity record);

    int insertSelective(FandomMemberEntity record);

    FandomMemberEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FandomMemberEntity record);

    int updateByPrimaryKey(FandomMemberEntity record);

    List<FandomMemberEntity> selectBySelective(FandomMemberEntity record);

    List<String> findFandomsByUserSn(String userSn);

    FandomMemberEntity selectByMemberAndFandom(FandomMemberEntity record);

}