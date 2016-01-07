package com.fansz.fandom.service;

import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.fandom.model.comment.AddCommentParam;
import com.fansz.fandom.model.comment.DelCommentParam;
import com.fansz.fandom.model.comment.PostCommentQueryParam;
import com.fansz.fandom.model.comment.PostCommentQueryResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Service;

/**
 * Created by root on 15-11-4.
 */
@Service
public interface CommentService {

    PostCommentQueryResult addComment(AddCommentParam commentPara) throws ApplicationException;

    /**
     * 删除评论
     * @param delCommentParam
     */
    void removeComment(DelCommentParam delCommentParam) throws ApplicationException;

    PageList<PostCommentQueryResult> getCommentsByPostidFromFandom(PostCommentQueryParam centQueryFromFandom, PageBounds pageBounds) throws ApplicationException;

}
