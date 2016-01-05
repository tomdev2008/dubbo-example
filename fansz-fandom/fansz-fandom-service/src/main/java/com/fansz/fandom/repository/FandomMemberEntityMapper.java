package com.fansz.fandom.repository;

import com.fansz.fandom.entity.FandomMemberEntity;
import com.fansz.fandom.model.fandom.FandomInfoResult;
import com.fansz.fandom.model.profile.ContactInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface FandomMemberEntityMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(FandomMemberEntity record);

    PageList<FandomInfoResult> findFandomsByMemberSn(String userSn, PageBounds pageBounds);

    FandomMemberEntity selectByMemberAndFandom(FandomMemberEntity record);

    PageList<ContactInfoResult> getFandomMembers(@Param("fandomId")String fandomId, @Param("memberSn")String memberSn, PageBounds pageBounds);
}