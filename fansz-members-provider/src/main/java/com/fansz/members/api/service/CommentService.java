package com.fansz.members.api.service;

import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentParam;
import org.springframework.stereotype.Service;

/**
 * Created by root on 15-11-4.
 */
@Service
public interface CommentService {

    PostCommentEntity addComment(CommentParam commentPara);

    /**
     * 删除评论
     * @param commentDelParam
     */
    void removeComment(CommentDelParam commentDelParam);

}
