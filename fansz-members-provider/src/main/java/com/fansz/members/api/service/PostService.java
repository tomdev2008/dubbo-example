package com.fansz.members.api.service;

import com.fansz.appservice.persistence.domain.Post;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.resource.param.PagePara;
import com.fansz.appservice.resource.param.PostParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface PostService {
    Post addPost( User user, PostParam postParam) throws IOException;

    void removePost(String id);

    Post getPost(
            User user,
            String id);

    void likePost(User user, String id);

    void unlikePost(User user, String id);

    List<Post> getFriendPosts(
            User user,
            @NotEmpty(message = "error.postId.empty")
            String friendId);

    List<Post> getFriendsPosts(
            User user,
            PagePara pagePara);

    List<Post> getAllFandomPosts(User user, PagePara pagePara);
}
