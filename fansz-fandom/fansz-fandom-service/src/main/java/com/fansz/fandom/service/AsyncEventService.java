package com.fansz.fandom.service;

import com.fansz.fandom.entity.FandomPostLikeEntity;
import com.fansz.fandom.entity.PostCommentEntity;
import com.fansz.fandom.model.post.AddPostParam;

import java.util.Date;
import java.util.Map;

/**
 * Created by allan on 16/2/1.
 */
public interface AsyncEventService {
    /**
     * 触发点赞事件
     *
     * @param postLikeEntity
     */
    void addLike(FandomPostLikeEntity postLikeEntity);

    /**
     * 触发发布fandom post事件
     *
     * @param postId
     * @param postTime
     * @param addPostParam
     */
    void addPost(Long postId, Date postTime, AddPostParam addPostParam);

    /**
     * 触发评论事件
     *
     * @param postComment
     */
    void addComment(PostCommentEntity postComment);

    /**
     * 触发用户变更事件
     *
     * @param user
     */
    void onUserChanged(Map<String,Object> user);

}
