package com.fansz.members.api.service.impl;


import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.api.repository.PostCommentEntityMapper;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentQueryFromFandomPram;
import com.fansz.members.model.comment.CommentQueryFromFandomResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 删除评论
     * @param commentDelParam
     */
    @Override
    public void removeComment(CommentDelParam commentDelParam) {
        postCommentEntityMapper.deleteMyComment(commentDelParam.getCommentatorSn(), commentDelParam.getCommentId());
    }

    @Override
    public PageList<CommentQueryFromFandomResult> getCommentsByPostidFromFandom(CommentQueryFromFandomPram commentQueryFromFandom, PageBounds pageBounds) {
        return postCommentEntityMapper.getCommentsByPostidFromFandom(commentQueryFromFandom.getPostId(),pageBounds);
    }

}
