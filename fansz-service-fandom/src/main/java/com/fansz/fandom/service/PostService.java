package com.fansz.fandom.service;


import com.fansz.fandom.model.post.*;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by root on 15-11-3.
 */
@Service
public interface PostService {

    Long addPost(AddPostParam addPostParam);

    void removePost(RemovePostParam removePostrParam);

    PostInfoResult getPost(GetPostByIdParam postParam);

    List<PostLikeInfoResult> listPostVotes(PostParam postParam);

    PageList<PostInfoResult> getFriendsPosts(String memberSn, PageBounds pageBounds);

    PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn, PageBounds pageBounds);

    PageList<PostInfoResult> searchPosts(SearchPostParam searchPostParam);

    void addLike(AddLikeParam addLikeParam);

    void deleteLike(DeleteLikeParam deleteLikeParam);

    PageList<PostInfoResult> getMemberFandomPosts(GetMemberFandomPostsParam getMemberFandomPostsParam);

    PageList<PostInfoResult> getFandomPosts(PostsQueryParam postsQueryParam);

    PageList<PostInfoResult> getPostsAllByMember(GetMemberPostsParam postParam);
}
