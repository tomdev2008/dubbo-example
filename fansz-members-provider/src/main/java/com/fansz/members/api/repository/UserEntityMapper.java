package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    int isExists(String mobile);

    int updatePassword(@Param("uid") Long uid, @Param("newPassword") String newPassword);

    UserEntity findByMoblie(String mobile);
}