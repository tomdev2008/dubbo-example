package com.fansz.fandom.service.impl;


import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.fandom.entity.PostCommentEntity;
import com.fansz.fandom.model.comment.AddCommentParam;
import com.fansz.fandom.model.comment.DelCommentParam;
import com.fansz.fandom.model.comment.PostCommentQueryParam;
import com.fansz.fandom.model.comment.PostCommentQueryResult;
import com.fansz.fandom.repository.FandomPostEntityMapper;
import com.fansz.fandom.repository.PostCommentEntityMapper;
import com.fansz.fandom.service.AsyncEventService;
import com.fansz.fandom.service.CommentService;
import com.fansz.fandom.tools.Constants;
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

    @Autowired
    private FandomPostEntityMapper fandomPostEntityMapper;

    @Autowired
    private AsyncEventService asyncEventService;

    @Override
    public PostCommentQueryResult addComment(AddCommentParam commentPara) throws ApplicationException {
        PostCommentEntity postCommentEntity = new PostCommentEntity();
        postCommentEntity.setPostId(commentPara.getPostId());
        postCommentEntity.setCommentatorSn(commentPara.getCurrentSn());
        postCommentEntity.setCommentContent(commentPara.getCommentContent());
        postCommentEntity.setCommentSource(commentPara.getCommentSource());
        postCommentEntity.setCommentParentId(commentPara.getCommentParentId());
        postCommentEntity.setCommentTime(new Date());
        postCommentEntityMapper.insert(postCommentEntity);
        fandomPostEntityMapper.incrCommentCountById(commentPara.getPostId());
        asyncEventService.addComment(postCommentEntity);
        return BeanTools.copyAs(postCommentEntity, PostCommentQueryResult.class);
    }

    /**
     * 删除评论
     *
     * @param delCommentParam
     */
    @Override
    public void removeComment(DelCommentParam delCommentParam) throws ApplicationException {
        PostCommentEntity postCommentEntity = postCommentEntityMapper.selectByIdAndSn(delCommentParam.getCurrentSn(), delCommentParam.getCommentId());
        if (postCommentEntity == null) {
            throw new ApplicationException(ErrorCode.COMMENT_NO_AUTHORITY_DELETE);
        }
        postCommentEntityMapper.deleteByPrimaryKey(postCommentEntity.getId());
        fandomPostEntityMapper.decrCommentCountById(postCommentEntity.getPostId());
    }

    @Override
    public PageList<PostCommentQueryResult> getCommentsByPostidFromFandom(PostCommentQueryParam commentQueryFromFandom, PageBounds pageBounds) throws ApplicationException {
        return postCommentEntityMapper.getCommentsByPostidFromFandom(commentQueryFromFandom.getPostId(), commentQueryFromFandom.getCommentSource(), pageBounds);
    }

}
