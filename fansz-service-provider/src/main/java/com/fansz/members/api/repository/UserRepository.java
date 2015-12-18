package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserEntity;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by allan on 15/11/22.
 */
@MapperScan
@Service
public interface UserRepository {

    UserEntity findByAccount(String userAccount);

    int deleteByPrimaryKey(Long userId);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    List<UserEntity> findByName(@Param("userName") String userName,PageBounds pageBounds);
}
