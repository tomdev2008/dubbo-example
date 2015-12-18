package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.fandom.SearchFandomResult;
import com.fansz.members.model.profile.ContactInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
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

    PageList<FandomInfoResult> getRecommendFandom(@Param("memberSn")String memberSn,PageBounds pageBounds);

    FandomInfoResult getFandomDetail(@Param("fandomId")Long fandomId,@Param("memberSn")String memberSn);

    List<FandomInfoResult> getFandomCategory(Long id);

    PageList<ContactInfoResult> getFandomMembers(@Param("fandomId")String fandomId,@Param("memberSn")String memberSn, PageBounds pageBounds);

    PageList<SearchFandomResult> searchFandoms(@Param("memberSn")String memberSn, @Param("searchVal")String searchVal, PageBounds pageBounds);

    int getCountByFandomName(String fandomName);
}