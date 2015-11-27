package com.fansz.members.api.service;

import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.comment.CommentPagePara;
import com.fansz.members.model.comment.CommentParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-4.
 */
public interface CommentService {

    PostCommentEntity addComment(UserEntity user, CommentParam commentPara);

    void removeComment(String id);

    List<PostCommentEntity> getComments( CommentPagePara commentPagePara);
}
