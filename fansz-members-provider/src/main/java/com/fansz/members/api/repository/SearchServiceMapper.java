package com.fansz.members.api.repository;

import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.model.post.PostLikeInfoResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by dell on 2015/12/2.
 */
@MapperScan
public interface SearchServiceMapper {

    List<UserInfoResult> searchMember(String keyWord,PageBounds pageBounds);

    List<PostInfoResult> searchPost(String keyWord,PageBounds pageBounds);

}
