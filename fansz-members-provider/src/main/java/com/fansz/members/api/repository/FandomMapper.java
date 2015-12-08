package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.SingleFandomEntity;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.fandom.FandomQueryParam;
import com.fansz.members.model.fandom.SearchFandomParam;
import com.fansz.members.model.fandom.SearchFandomResult;
import com.fansz.members.model.profile.ContactInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

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

    List<FandomInfoResult> getRecommendFandom(PageBounds pageBounds);

    List<FandomInfoResult> getFandomCategory(Long id);

    PageList<ContactInfoResult> getFandomMembers(@Param("fandomId")String fandomId,@Param("memberSn")String memberSn, PageBounds pageBounds);

    SingleFandomEntity findFandomInfo(@Param("id") long id);

    PageList<SearchFandomResult> searchFandoms(@Param("memberSn")String memberSn, @Param("searchVal")String searchVal, PageBounds pageBounds);
}