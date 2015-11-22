package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserEntity;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by allan on 15/11/22.
 */
@MapperScan
public interface UserRepository {

    UserEntity findByAccount(String userAccount);

    int deleteByPrimaryKey(Long userId);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}
