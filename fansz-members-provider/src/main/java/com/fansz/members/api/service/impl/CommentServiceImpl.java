package com.fansz.members.api.service.impl;

import com.fansz.appservice.persistence.domain.Comment;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.persistence.mapper.CommentMapper;
import com.fansz.appservice.persistence.mapper.PostMapper;
import com.fansz.appservice.resource.param.CommentPagePara;
import com.fansz.appservice.resource.param.CommentPara;
import com.fansz.appservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    PostMapper postMapper;

    @Override
    public Comment addComment(CommentPara commentPara) throws IOException {

        Comment comment = new Comment(user, commentPara);
        commentMapper.save(comment);

        postMapper.modifyCommentsCount(comment.getPostId(), 1);
        return comment;
    }

    @Override
    public void removeComment(String id) {
        commentMapper.removeComment(id);
        Comment comment = commentMapper.getComment(id);
        postMapper.modifyCommentsCount(comment.getPostId(), -1);
    }

    @Override
    public List<Comment> getComments(CommentPagePara commentPagePara) {
        return commentMapper.getComments(commentPagePara);
    }
}
