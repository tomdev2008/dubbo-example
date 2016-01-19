package com.fansz.feeds.service.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.db.entity.PushComment;
import com.fansz.db.entity.User;
import com.fansz.db.repository.NewsfeedsCommentDAO;
import com.fansz.db.repository.PushCommentDAO;
import com.fansz.db.repository.UserDAO;
import com.fansz.event.model.AddCommentEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.feeds.service.NewsfeedsCommentService;
import com.fansz.newsfeeds.model.comment.DelCommentParam;
import com.fansz.newsfeeds.model.comment.NewsfeedsCommentParam;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.pub.utils.StringTools;
import com.fansz.redis.UserTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by dell on 2015/12/28.
 */
@Service("newsfeedsCommentService")
public class NewsfeedsCommentServiceImpl implements NewsfeedsCommentService {

    @Autowired
    private NewsfeedsCommentDAO newsfeedsCommentDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private PushCommentDAO pushCommentDAO;

    @Resource(name = "userTemplate")
    private UserTemplate userTemplate;

    @Override
    public NewsfeedsPostComment savePostComment(NewsfeedsCommentParam commentPara) {
        NewsfeedsPostComment newsfeedsPostComment = BeanTools.copyAs(commentPara, NewsfeedsPostComment.class);
        Date now = DateTools.getSysDate();
        newsfeedsPostComment.setCommentatorSn(commentPara.getCurrentSn());
        newsfeedsPostComment.setCommentTime(now);
        newsfeedsCommentDAO.save(newsfeedsPostComment);

        PushComment pushComment = new PushComment();
        pushComment.setCommentId(newsfeedsPostComment.getId());
        pushComment.setMemberSn(commentPara.getCurrentSn());
        pushComment.setCreatetime(now);
        pushComment.setPostId(commentPara.getPostId());
        pushCommentDAO.save(pushComment);

        AddCommentEvent addCommentEvent = new AddCommentEvent(newsfeedsPostComment.getId(), newsfeedsPostComment.getPostId(), newsfeedsPostComment.getCommentatorSn(), newsfeedsPostComment.getCommentContent());
        eventProducer.produce(AsyncEventType.ADD_COMMENT,addCommentEvent);
        return newsfeedsPostComment;
    }

    @Override
    public String deleteCommet(DelCommentParam delCommentParam) {
        NewsfeedsPostComment newsfeedsPostComment = newsfeedsCommentDAO.load(delCommentParam.getCommentId());
        if (newsfeedsPostComment != null) {
            if (!StringTools.equals(newsfeedsPostComment.getCommentatorSn(), delCommentParam.getCurrentSn())) {
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
    public PostCommentQueryResult assemblyPostCommentResult(NewsfeedsPostComment newsfeedsPostComment) {
        PostCommentQueryResult postCommentQueryResult = BeanTools.copyAs(newsfeedsPostComment, PostCommentQueryResult.class);
        Map<String, String> userMap = userTemplate.get(newsfeedsPostComment.getCommentatorSn());
        postCommentQueryResult.setCommentatorNickname(userMap.get("nickname"));
        postCommentQueryResult.setCommentatorAvatar(userMap.get("member_avatar"));
        if (newsfeedsPostComment.getCommentParentId() != null) {
            NewsfeedsPostComment originComment = newsfeedsCommentDAO.load(newsfeedsPostComment.getCommentParentId());
            Map<String, String> originUserMap = userTemplate.get(originComment.getCommentatorSn());
            postCommentQueryResult.setOriginAvatar(originUserMap.get("member_avatar"));
            postCommentQueryResult.setOriginSn(originUserMap.get("sn"));
            postCommentQueryResult.setOriginNickname(originUserMap.get("nickname"));
            postCommentQueryResult.setOriginContent(originComment.getCommentContent());
        }
        return postCommentQueryResult;
    }
}