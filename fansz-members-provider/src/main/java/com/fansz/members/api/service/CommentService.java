package com.fansz.members.api.service;

import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.comment.CommentPagedParam;
import com.fansz.members.model.comment.CommentParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public interface CommentService {

    PostCommentEntity addComment(UserEntity user, CommentParam commentPara);

    void removeComment(String id);

    List<PostCommentEntity> getComments( CommentPagedParam commentPagePara);
}
