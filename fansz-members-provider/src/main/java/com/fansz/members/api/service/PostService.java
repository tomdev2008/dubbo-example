package com.fansz.members.api.service;

import com.fansz.appservice.persistence.domain.Post;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.resource.param.PagePara;
import com.fansz.appservice.resource.param.PostParam;
import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface PostService {
    FandomPostEntity addPost(UserEntity user, PostParam postParam) throws IOException;

    void removePost(String id);

    FandomPostEntity getPost(
            UserEntity user,
            String id);

    void likePost(UserEntity user, String id);

    void unlikePost(UserEntity user, String id);

    List<FandomPostEntity> getFriendPosts(
            UserEntity user,
            String friendId);

    List<FandomPostEntity> getFriendsPosts(
            UserEntity user,
            PageBounds pagePara);

    List<FandomPostEntity> getAllFandomPosts(UserEntity user, PageBounds pagePara);
}
