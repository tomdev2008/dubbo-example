package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.friendship.FocusedFandomResult;
import com.fansz.members.model.param.UserInfoResult;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface UserEntityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByUidSelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    int isExists(String mobile);

    int updatePassword(@Param("uid") Long uid, @Param("newPassword") String newPassword);

    UserEntity findByMoblie(String mobile);

    UserInfoResult findByUid(String uid);

    List<FocusedFandomResult> findFandomById(Long id);

    List<UserInfoResult> findByMobiles(List<String> mobiles);
}