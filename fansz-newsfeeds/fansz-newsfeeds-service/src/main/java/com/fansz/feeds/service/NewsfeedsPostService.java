package com.fansz.feeds.service;


import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import com.fansz.newsfeeds.model.post.RemovePostParam;


/**
 * Created by root on 15-11-3.
 */
public interface NewsfeedsPostService {

    Long addPost(AddPostParam addPostParam);

    PostInfoResult getPost(Long postId);

    NewsfeedsPost deletePostById(RemovePostParam postParam);

    String saveNewsfeedLike(GetPostByIdParam postParam);

    String deleteNewsfeedLike(GetPostByIdParam postParam);

}
