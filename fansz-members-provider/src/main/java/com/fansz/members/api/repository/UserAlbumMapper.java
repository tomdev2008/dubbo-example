package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserAlbumEntity;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserAlbumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAlbumEntity record);

    int insertSelective(UserAlbumEntity record);

    UserAlbumEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAlbumEntity record);

    int updateByPrimaryKey(UserAlbumEntity record);
}