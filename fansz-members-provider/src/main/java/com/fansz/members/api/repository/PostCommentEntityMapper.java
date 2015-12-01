package com.fansz.members.api.repository;

import com.fansz.members.api.entity.PostCommentEntity;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface PostCommentEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PostCommentEntity record);

    int insertSelective(PostCommentEntity record);

    PostCommentEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostCommentEntity record);

    int updateByPrimaryKey(PostCommentEntity record);
}