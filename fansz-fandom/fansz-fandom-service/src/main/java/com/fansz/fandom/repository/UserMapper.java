package com.fansz.fandom.repository;

import com.fansz.fandom.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

@MapperScan
@Service
public interface UserMapper {

    int updateByUidSelective(UserInfoResult record);

    PageList<UserInfoResult> searchMembers(@Param("nickname") String nickname, @Param("mobile") String mobile, @Param("memberType") String memberType, @Param("loginname") String loginname, PageBounds pageBounds);

    PageList<UserInfoResult> searchMembersByKey(@Param("searchKey") String searchKey, @Param("sn") String sn, PageBounds pageBounds);

}