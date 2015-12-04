package com.fansz.members.api.repository;

import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.model.comment.CommentQueryFromFandomResult;
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

    PageList<CommentQueryFromFandomResult> getCommentsByPostidFromFandom(Long postId, PageBounds pageBounds);


    /**
     * 删除我的评论
     *
     * @param sn 评论者sn
     * @param id 评论ID
     * @return
     */
    int deleteMyComment(@Param("sn") String sn, @Param("id") long id);
}