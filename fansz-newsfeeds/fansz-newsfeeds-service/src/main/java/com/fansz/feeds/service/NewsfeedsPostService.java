package com.fansz.feeds.service;


import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import org.springframework.stereotype.Service;


/**
 * Created by root on 15-11-3.
 */
public interface NewsfeedsPostService {

    Long addPost(AddPostParam addPostParam);

    PostInfoResult getPost(Long postId);
}
