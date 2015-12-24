package com.fansz.service.api.repository;

import com.fansz.service.api.entity.FandomPostLikeEntity;
import com.fansz.service.model.post.PostLikeInfoResult;
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