package com.fansz.members.api.repository;

import com.fansz.members.api.entity.PostCommentEntity;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface PostCommentEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostCommentEntity record);

    int insertSelective(PostCommentEntity record);

    PostCommentEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostCommentEntity record);

    int updateByPrimaryKey(PostCommentEntity record);

    List getCommentsByPostidFromFandom(Integer postId);

}