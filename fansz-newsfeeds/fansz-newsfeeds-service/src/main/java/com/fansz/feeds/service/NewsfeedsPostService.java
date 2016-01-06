package com.fansz.feeds.service;


import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.newsfeeds.model.post.*;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import org.springframework.stereotype.Service;


/**
 * Created by root on 15-11-3.
 */
public interface NewsfeedsPostService {

    Long addPost(AddPostParam addPostParam);

    PostInfoResult getPost(GetPostByIdParam postParam);

    QueryResult<PostInfoResult> findNewsfeedsListByMemberSn(GetPostsParam postsParam);

    QueryResult<PostInfoResult> findFriendsNewsfeedsListBySn(GetMemberPostsParam memberPostsParam);

    NewsfeedsPost deletePostById(RemovePostParam postParam);

    String saveNewsfeedLike(GetPostByIdParam postParam);

    String deleteNewsfeedLike(GetPostByIdParam postParam);

}
