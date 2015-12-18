package com.fansz.members.api.service.impl;


import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.api.repository.PostCommentEntityMapper;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentFromFandomQueryParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentQueryFromFandomResult;
import com.fansz.members.tools.Constants;
import com.fansz.pub.utils.BeanTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by root on 15-11-4.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostCommentEntityMapper postCommentEntityMapper;

    @Override
    public CommentQueryFromFandomResult addComment(CommentParam commentPara) {
        PostCommentEntity postCommentEntity = new PostCommentEntity();
        postCommentEntity.setPostId(commentPara.getPostId());
        postCommentEntity.setCommentatorSn(commentPara.getCommentatorSn());
        postCommentEntity.setCommentContent(commentPara.getCommentContent());
        postCommentEntity.setCommentSource(commentPara.getCommentSource());
        postCommentEntity.setCommentParentId(commentPara.getCommentParentId());
        postCommentEntity.setCommentTime(new Date());
        postCommentEntityMapper.insert(postCommentEntity);
        return BeanTools.copyAs(postCommentEntity, CommentQueryFromFandomResult.class);
    }

    /**
     * 删除评论
     *
     * @param commentDelParam
     */
    @Override
    public void removeComment(CommentDelParam commentDelParam) {
        int deleted=postCommentEntityMapper.deleteMyComment(commentDelParam.getCommentatorSn(), commentDelParam.getCommentId());
        if(deleted!=1){
            throw new ApplicationException(Constants.COMMENT_NOT_EXISTS,"Comment not exists");
        }
    }

    @Override
    public PageList<CommentQueryFromFandomResult> getCommentsByPostidFromFandom(CommentFromFandomQueryParam commentQueryFromFandom, PageBounds pageBounds) {
        return postCommentEntityMapper.getCommentsByPostidFromFandom(commentQueryFromFandom.getPostId(),commentQueryFromFandom.getCommentSource(), pageBounds);
    }

}
