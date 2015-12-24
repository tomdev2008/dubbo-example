package com.fansz.service.api.repository;

import com.fansz.service.api.entity.PostCommentEntity;
import com.fansz.service.model.comment.PostCommentQueryResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface PostCommentEntityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PostCommentEntity record);

    int insertSelective(PostCommentEntity record);

    PostCommentEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PostCommentEntity record);

    int updateByPrimaryKey(PostCommentEntity record);

    PageList<PostCommentQueryResult> getCommentsByPostidFromFandom(@Param("postId") Long postId, @Param("commentSource") String commentSource, PageBounds pageBounds);


    /**
     * 删除我的评论
     *
     * @param sn 评论者sn
     * @param id 评论ID
     * @return
     */
    PostCommentEntity selectByIdAndSn(@Param("sn") String sn, @Param("id") long id);
}