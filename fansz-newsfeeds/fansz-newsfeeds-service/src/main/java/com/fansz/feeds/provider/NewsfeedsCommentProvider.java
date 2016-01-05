package com.fansz.feeds.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.db.entity.User;
import com.fansz.db.repository.NewsfeedsCommentDAO;
import com.fansz.db.repository.UserDAO;
import com.fansz.feeds.service.NewsfeedsCommentService;
import com.fansz.newsfeeds.api.NewsfeedsCommentApi;
import com.fansz.newsfeeds.model.comment.NewsfeedsCommentParam;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import com.fansz.pub.utils.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2015/12/28.
 */
@Component("newsfeedsCommentProvider")
public class NewsfeedsCommentProvider extends AbstractProvider implements NewsfeedsCommentApi {

    @Autowired
    private NewsfeedsCommentService newsfeedsCommentService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private NewsfeedsCommentDAO newsfeedsCommentDAO;

    @Override
    public CommonResult<PostCommentQueryResult> addPostComment(NewsfeedsCommentParam commentPara) {
        NewsfeedsPostComment newsfeedsPostComment = newsfeedsCommentService.savePostComment(commentPara);
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

        return renderSuccess(postCommentQueryResult);
    }

    @Override
    public CommonResult<PostCommentQueryResult> removeCommet(NewsfeedsCommentParam delCommentParam) {
        String code = newsfeedsCommentService.deleteCommet(delCommentParam);
        if (null == code) {
            PostCommentQueryResult postCommentQueryResult = new PostCommentQueryResult();
            postCommentQueryResult.setId(delCommentParam.getId());
            return renderSuccess(postCommentQueryResult);
        }
        return renderFail(code);
    }
}
