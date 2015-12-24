package com.fansz.service.api.service;

import com.fansz.service.model.comment.DelCommentParam;
import com.fansz.service.model.comment.PostCommentQueryParam;
import com.fansz.service.model.comment.AddCommentParam;
import com.fansz.service.model.comment.PostCommentQueryResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Service;

/**
 * Created by root on 15-11-4.
 */
@Service
public interface CommentService {

    PostCommentQueryResult addComment(AddCommentParam commentPara);

    /**
     * 删除评论
     * @param delCommentParam
     */
    void removeComment(DelCommentParam delCommentParam);

    PageList<PostCommentQueryResult> getCommentsByPostidFromFandom(PostCommentQueryParam centQueryFromFandom, PageBounds pageBounds);

}
