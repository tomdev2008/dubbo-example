package com.fansz.members.api.service.impl;


import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.model.comment.CommentPagePara;
import com.fansz.members.model.comment.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Override
    public PostCommentEntity addComment(UserEntity user, CommentParam commentPara) {
      return null;
    }

    @Override
    public void removeComment(String id) {

    }

    @Override
    public List<PostCommentEntity> getComments(CommentPagePara commentPagePara) {
        return null;
    }
}
