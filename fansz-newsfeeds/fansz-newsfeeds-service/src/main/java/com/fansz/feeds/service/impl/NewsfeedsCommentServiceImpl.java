package com.fansz.feeds.service.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.db.entity.User;
import com.fansz.db.repository.NewsfeedsCommentDAO;
import com.fansz.db.repository.UserDAO;
import com.fansz.feeds.service.NewsfeedsCommentService;
import com.fansz.newsfeeds.model.comment.DelCommentParam;
import com.fansz.newsfeeds.model.comment.NewsfeedsCommentParam;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.pub.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 2015/12/28.
 */
@Service("newsfeedsCommentService")
public class NewsfeedsCommentServiceImpl implements NewsfeedsCommentService{

    @Autowired
    private NewsfeedsCommentDAO newsfeedsCommentDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public NewsfeedsPostComment savePostComment(NewsfeedsCommentParam commentPara){
        NewsfeedsPostComment newsfeedsPostComment = BeanTools.copyAs(commentPara,NewsfeedsPostComment.class);
        newsfeedsPostComment.setCommentatorSn(commentPara.getCurrentSn());
        newsfeedsPostComment.setCommentTime(DateTools.getSysDate());
        newsfeedsCommentDAO.save(newsfeedsPostComment);
        return newsfeedsPostComment;
    }

    @Override
    public String deleteCommet(DelCommentParam delCommentParam) {
        NewsfeedsPostComment newsfeedsPostComment = newsfeedsCommentDAO.load(delCommentParam.getCommentId());
        if(newsfeedsPostComment!=null){
            if(!StringTools.equals(newsfeedsPostComment.getCommentatorSn(),delCommentParam.getCurrentSn())){
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

    @Override
    public PostCommentQueryResult assemblyPostCommentResult(NewsfeedsPostComment newsfeedsPostComment){
        PostCommentQueryResult postCommentQueryResult = BeanTools.copyAs(newsfeedsPostComment, PostCommentQueryResult.class);
        User commentUser = userDAO.findBySn(newsfeedsPostComment.getCommentatorSn());
        postCommentQueryResult.setCommentatorNickname(commentUser.getNickname());
        postCommentQueryResult.setCommentatorAvatar(commentUser.getMemberAvatar());
        if (newsfeedsPostComment.getCommentParentId() != null) {
            NewsfeedsPostComment originComment = newsfeedsCommentDAO.load(newsfeedsPostComment.getCommentParentId());
            User originUser = userDAO.findBySn(originComment.getCommentatorSn());
            postCommentQueryResult.setOriginAvatar(originUser.getMemberAvatar());
            postCommentQueryResult.setOriginSn(originUser.getSn());
            postCommentQueryResult.setOriginNickname(originUser.getNickname());
            postCommentQueryResult.setOriginContent(originComment.getCommentContent());
        }
        return postCommentQueryResult;
    }
}