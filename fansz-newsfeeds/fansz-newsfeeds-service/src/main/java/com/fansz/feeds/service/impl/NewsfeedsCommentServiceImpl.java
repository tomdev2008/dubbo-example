package com.fansz.feeds.service.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.db.repository.NewsfeedsCommentDAO;
import com.fansz.feeds.service.NewsfeedsCommentService;
import com.fansz.newsfeeds.model.comment.NewsfeedsCommentParam;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/12/28.
 */
@Service("newsfeedsCommentService")
public class NewsfeedsCommentServiceImpl implements NewsfeedsCommentService{

    @Autowired
    private NewsfeedsCommentDAO newsfeedsCommentDAO;

    @Override
    public NewsfeedsPostComment savePostComment(NewsfeedsCommentParam commentPara){
        NewsfeedsPostComment newsfeedsPostComment = BeanTools.copyAs(commentPara,NewsfeedsPostComment.class);
        newsfeedsCommentDAO.save(newsfeedsPostComment);
        //添加评论条数

        return newsfeedsPostComment;
    };

    @Override
    public String deleteCommet(NewsfeedsCommentParam delCommentParam) {
        NewsfeedsPostComment newsfeedsPostComment = newsfeedsCommentDAO.load(delCommentParam.getId());
        if(newsfeedsPostComment!=null){
            if(!StringTools.equals(newsfeedsPostComment.getCommentatorSn(),delCommentParam.getCommentatorSn())){
                return ErrorCode.COMMENT_NO_AUTHORITY_DELETE.getCode();
            }
            newsfeedsCommentDAO.delete(newsfeedsPostComment);
            return null;
        }
        return ErrorCode.COMMENT_NO_AUTHORITY_DELETE.getCode();
    }

    @Override
    public String deleteCommetByPostId(Long postId) {
        newsfeedsCommentDAO.removeCommetByPostId(postId);
        return null;
    }
}
