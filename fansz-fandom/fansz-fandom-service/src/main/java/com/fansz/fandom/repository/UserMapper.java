package com.fansz.fandom.repository;

import com.fansz.fandom.entity.UserEntity;
import com.fansz.fandom.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import java.util.List;

@MapperScan
@Service
public interface UserMapper {

    int updateByUidSelective(UserEntity record);

    int isExistsNickname(@Param("nickname") String nickname, @Param("exlucdeSn") String exlucdeSn);

    UserEntity selectByUid(String uid);

    PageList<UserInfoResult> searchMembers(@Param("nickname") String nickname, @Param("mobile") String mobile, @Param("memberType") String memberType, @Param("loginname") String loginname, PageBounds pageBounds);

    PageList<UserInfoResult> searchMembersByKey(@Param("searchKey") String searchKey, @Param("sn") String sn, PageBounds pageBounds);

}