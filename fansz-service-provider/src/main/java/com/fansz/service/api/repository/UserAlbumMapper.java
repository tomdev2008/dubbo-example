package com.fansz.service.api.repository;

import com.fansz.service.api.entity.UserAlbumEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

@MapperScan
@Service
public interface UserAlbumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAlbumEntity record);

    int insertSelective(UserAlbumEntity record);

    UserAlbumEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAlbumEntity record);

    int updateByPrimaryKey(UserAlbumEntity record);
}