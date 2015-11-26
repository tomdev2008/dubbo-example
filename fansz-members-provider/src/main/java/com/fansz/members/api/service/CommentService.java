package com.fansz.members.api.service;

import com.fansz.members.model.param.CommentPagePara;
import com.fansz.members.model.param.CommentPara;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-4.
 */
public interface CommentService {
    Comment addComment(@Valid User user, CommentPara commentPara) throws IOException;

    void removeComment(String id);

    List<Comment> getComments(@Valid CommentPagePara commentPagePara);
}
