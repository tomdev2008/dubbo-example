package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import java.util.List;

@MapperScan
@Service
public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    List<UserEntity> getFandomFollowers(Integer id);

}