package com.fansz.fandom.repository;

import com.fansz.fandom.entity.UserAlbumEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

@MapperScan
@Service
public interface UserAlbumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAlbumEntity record);

    int insertSelective(UserAlbumEntity record);
}