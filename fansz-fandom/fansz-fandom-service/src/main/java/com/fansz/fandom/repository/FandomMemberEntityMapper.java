package com.fansz.fandom.repository;

import com.fansz.fandom.entity.FandomMemberEntity;
import com.fansz.fandom.model.fandom.FandomInfoResult;
import com.fansz.fandom.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface FandomMemberEntityMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(FandomMemberEntity record);

    PageList<FandomInfoResult> findFandomsByMemberSn(String userSn, PageBounds pageBounds);

    FandomMemberEntity selectByMemberAndFandom(FandomMemberEntity record);

    PageList<UserInfoResult> getFandomMembers(@Param("fandomId")String fandomId, @Param("memberSn")String memberSn, PageBounds pageBounds);
}