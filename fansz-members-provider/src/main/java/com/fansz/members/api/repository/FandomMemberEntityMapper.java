package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomMemberEntity;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
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

    PageList<FandomInfoResult> findFandomsByMemberSn(String userSn, PageBounds pageBounds);

    FandomMemberEntity selectByMemberAndFandom(FandomMemberEntity record);

}