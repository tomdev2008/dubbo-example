package com.fansz.members.api.service;

import com.fansz.members.api.entity.PostCommentEntity;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentQueryFromFandomPram;
import com.fansz.members.model.comment.CommentQueryFromFandomResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Service;

/**
 * Created by root on 15-11-4.
 */
@Service
public interface CommentService {

    PostCommentEntity addComment(CommentParam commentPara);

    /**
     * 删除评论
     * @param commentDelParam
     */
    void removeComment(CommentDelParam commentDelParam);

    PageList<CommentQueryFromFandomResult> getCommentsByPostidFromFandom(CommentQueryFromFandomPram centQueryFromFandom, PageBounds pageBounds);

}
