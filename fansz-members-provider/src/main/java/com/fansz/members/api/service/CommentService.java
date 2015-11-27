package com.fansz.members.api.service;

import com.fansz.members.model.param.CommentPagePara;
import com.fansz.members.model.param.CommentPara;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public interface CommentService {
    Comment addComment(CommentPara commentPara) throws IOException;

    void removeComment(String id);

    List<Comment> getComments(@Valid CommentPagePara commentPagePara);
}
