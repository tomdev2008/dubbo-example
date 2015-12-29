package com.fansz.feeds.service;


import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;


/**
 * Created by root on 15-11-3.
 */
public interface NewsfeedsPostService {
    Long addPost(AddPostParam addPostParam);

    PostInfoResult getPost(GetPostByIdParam postParam);

    QueryResult<PostInfoResult> findNewsfeedsListByMemberSn(String memberSn, Page page);

    QueryResult<PostInfoResult> findFriendsNewsfeedsListBySn(String memberSn, String friendSn, Page page);
}
