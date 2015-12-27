package com.fansz.fandom.repository;

import com.fansz.fandom.entity.FandomPostLikeEntity;
import com.fansz.fandom.model.post.PostLikeInfoResult;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface FandomPostLikeEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FandomPostLikeEntity record);

    int insertSelective(FandomPostLikeEntity record);

    FandomPostLikeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FandomPostLikeEntity record);

    int updateByPrimaryKey(FandomPostLikeEntity record);

    List<PostLikeInfoResult> listPostVotes(Long id);

    int deleteMyLike(@Param("sn") String sn, @Param("postId") long postId);

    int isLiked(@Param("sn") String sn, @Param("postId") long postId);

}