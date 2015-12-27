package com.fansz.feeds.service;


import com.fansz.newsfeed.model.post.AddPostParam;
import com.fansz.newsfeed.model.post.GetPostByIdParam;
import com.fansz.newsfeed.model.post.PostInfoResult;
import org.springframework.stereotype.Service;


/**
 * Created by root on 15-11-3.
 */
@Service
public interface PostService {
    Long addPost(AddPostParam addPostParam);

    PostInfoResult getPost(GetPostByIdParam postParam);
}
