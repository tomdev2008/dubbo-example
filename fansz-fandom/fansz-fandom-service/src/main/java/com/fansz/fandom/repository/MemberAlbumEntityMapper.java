package com.fansz.fandom.repository;

import com.fansz.fandom.entity.MemberAlbumEntity;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface MemberAlbumEntityMapper {
    List<String> getImages(String id);
}