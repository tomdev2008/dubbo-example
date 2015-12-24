package com.fansz.service.api.service.impl;


import com.fansz.service.api.entity.PostCommentEntity;
import com.fansz.service.api.repository.FandomPostEntityMapper;
import com.fansz.service.api.repository.PostCommentEntityMapper;
import com.fansz.service.api.service.CommentService;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.model.comment.DelCommentParam;
import com.fansz.service.model.comment.PostCommentQueryParam;
import com.fansz.service.model.comment.AddCommentParam;
import com.fansz.service.model.comment.PostCommentQueryResult;
import com.fansz.service.tools.Constants;
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

    @Override
    public PostCommentQueryResult addComment(AddCommentParam commentPara) {
        PostCommentEntity postCommentEntity = new PostCommentEntity();
        postCommentEntity.setPostId(commentPara.getPostId());
        postCommentEntity.setCommentatorSn(commentPara.getCurrentSn());
        postCommentEntity.setCommentContent(commentPara.getCommentContent());
        postCommentEntity.setCommentSource(commentPara.getCommentSource());
        postCommentEntity.setCommentParentId(commentPara.getCommentParentId());
        postCommentEntity.setCommentTime(new Date());
        postCommentEntityMapper.insert(postCommentEntity);
        fandomPostEntityMapper.incrCommentCountById(commentPara.getPostId());
        return BeanTools.copyAs(postCommentEntity, PostCommentQueryResult.class);
    }

    /**
     * 删除评论
     *
     * @param delCommentParam
     */
    @Override
    public void removeComment(DelCommentParam delCommentParam) {
        PostCommentEntity postCommentEntity=postCommentEntityMapper.selectByIdAndSn(delCommentParam.getCurrentSn(), delCommentParam.getCommentId());
        if(postCommentEntity==null){
            throw new ApplicationException(Constants.COMMENT_NO_AUTHORITY_DELETE,"No authority to delete Comment");
        }
        postCommentEntityMapper.deleteByPrimaryKey(postCommentEntity.getId());
        fandomPostEntityMapper.decrCommentCountById(postCommentEntity.getPostId());
    }

    @Override
    public PageList<PostCommentQueryResult> getCommentsByPostidFromFandom(PostCommentQueryParam commentQueryFromFandom, PageBounds pageBounds) {
        return postCommentEntityMapper.getCommentsByPostidFromFandom(commentQueryFromFandom.getPostId(),commentQueryFromFandom.getCommentSource(), pageBounds);
    }

}
