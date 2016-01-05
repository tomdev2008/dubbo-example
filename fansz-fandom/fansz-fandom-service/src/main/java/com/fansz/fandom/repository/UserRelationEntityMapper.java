package com.fansz.fandom.repository;

import com.fansz.fandom.entity.UserRelationEntity;
import com.fansz.fandom.model.profile.ContactInfoResult;
import com.fansz.fandom.model.relationship.FriendInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface UserRelationEntityMapper {
    /**
     * 查询用户是否是好友
     *
     * @param memberSn
     * @param friendSn
     * @return
     */
    UserRelationEntity findFriendRelationBySns(@Param("memberSn") String memberSn, @Param("friendSn") String friendSn);

}