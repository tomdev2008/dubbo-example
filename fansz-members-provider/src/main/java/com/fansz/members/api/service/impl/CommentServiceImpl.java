package com.fansz.members.api.service.impl;


import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.PostCommentEntityMapper;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.model.comment.CommentPagedParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentQueryFromFandom;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostCommentEntityMapper postCommentEntityMapper;
    @Override
    public PostCommentEntity addComment(UserEntity user, CommentParam commentPara) {
      return null;
    }

    @Override
    public void removeComment(String id) {

    }

    @Override
    public PageList<PostCommentEntity> getCommentsByPostidFromFandom(CommentQueryFromFandom commentQueryFromFandom) {
        int postId = commentQueryFromFandom.getPostId();
        postCommentEntityMapper.getCommentsByPostidFromFandom(postId);
        return null;
    }


}
