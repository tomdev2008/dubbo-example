package com.fansz.members.api.service.impl;


import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.PostCommentEntityMapper;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentPagedParam;
import com.fansz.members.model.comment.CommentParam;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostCommentEntityMapper postCommentEntityMapper;

    @Override
    public PostCommentEntity addComment(CommentParam commentPara) {
        PostCommentEntity postCommentEntity = new PostCommentEntity();
        postCommentEntity.setPostId(commentPara.getPostId());
        postCommentEntity.setCommentatorSn(commentPara.getCommentatorSn());
        postCommentEntity.setCommentContent(commentPara.getCommentContent());
        postCommentEntity.setCommentSource(commentPara.getCommentSource());
        postCommentEntity.setCommentParentId(commentPara.getCommentParentId());
        postCommentEntityMapper.insert(postCommentEntity);
      return null;
    }

    @Override
    public void removeComment(CommentDelParam commentDelParam) {
        postCommentEntityMapper.deleteByPrimaryKey(commentDelParam.getCommentId());
    }

    @Override
    public List<PostCommentEntity> getComments(CommentPagedParam commentPagePara) {
        return null;
    }
}
