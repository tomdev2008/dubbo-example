package com.fansz.members.api.service;

import com.fansz.members.model.Post;
import com.fansz.members.model.User;
import com.fansz.members.model.param.PagePara;
import com.fansz.members.model.param.PostParam;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
@Service
public interface PostService {
    Post addPost(User user, PostParam postParam) throws IOException;

    void removePost(String id);

    Post getPost(
            User user,
            String id);

    void likePost(User user, String id);

    void unlikePost(User user, String id);

    List<Post> getFriendPosts(
            User user,
            String friendId);

    List<Post> getFriendsPosts(
            User user,
            PagePara pagePara);

    List<Post> getAllFandomPosts(User user, PagePara pagePara);
}
