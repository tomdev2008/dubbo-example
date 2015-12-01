package com.fansz.members.api.service;

import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentPagedParam;
import com.fansz.members.model.comment.CommentParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public interface CommentService {

    PostCommentEntity addComment(CommentParam commentPara);

    void removeComment(CommentDelParam commentDelParam);

    List<PostCommentEntity> getComments(CommentPagedParam commentPagePara);
}
