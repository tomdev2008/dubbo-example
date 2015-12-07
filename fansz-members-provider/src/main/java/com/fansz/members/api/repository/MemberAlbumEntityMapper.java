package com.fansz.members.api.repository;

import com.fansz.members.api.entity.MemberAlbumEntity;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface MemberAlbumEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberAlbumEntity record);

    int insertSelective(MemberAlbumEntity record);

    MemberAlbumEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberAlbumEntity record);

    int updateByPrimaryKey(MemberAlbumEntity record);

    List<String> getImages(String id);
}