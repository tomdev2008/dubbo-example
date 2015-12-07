package com.fansz.members.api.service;


import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.model.post.*;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by root on 15-11-3.
 */
@Service
public interface PostService {

    FandomPostEntity addPost(AddPostParam addPostParam);

    void removePost(RemovePostParam removePostrParam);

    PostInfoResult getPost(PostParam postParam);

    List<PostLikeInfoResult> listPostVotes(PostParam postParam);

    PageList<PostInfoResult> getFriendsPosts(String memberSn, PageBounds pageBounds);

    PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn, PageBounds pageBounds);

    PageList<SearchPostResult> searchPosts(SearchPostParam searchPostParam);
    void addLike(AddLikeParam addLikeParam);

    void deleteLike(DeleteLikeParam deleteLikeParam);

    PageList<MemberPostInfoResult> getMemberFandomPosts(GetMemberFandomPostsParam getMemberFandomPostsParam);

    PageList<FandomPostInfoResult> getFandomPosts(PostsQueryParam postsQueryParam);
}
