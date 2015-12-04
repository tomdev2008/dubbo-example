package com.fansz.members.api.service.impl;


import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.FandomPostLikeEntity;
import com.fansz.members.api.entity.MemberPostEntity;
import com.fansz.members.api.repository.FandomPostEntityMapper;
import com.fansz.members.api.repository.FandomPostLikeEntityMapper;
import com.fansz.members.api.service.PostService;
import com.fansz.members.model.post.*;
import com.fansz.members.tools.BeanTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private FandomPostEntityMapper fandomPostEntityMapper;

    @Autowired
    private FandomPostLikeEntityMapper fandomPostLikeEntityMapper;

    @Override
    public FandomPostEntity addPost(AddPostParam addPostParam) {
        FandomPostEntity fandomPostEntity = BeanTools.copyAs(addPostParam, FandomPostEntity.class);
        fandomPostEntityMapper.insert(fandomPostEntity);
        return fandomPostEntity;
    }

    @Override
    public void removePost(RemovePostParam removePostrParam) {
        fandomPostEntityMapper.deleteByPrimaryKey(removePostrParam.getPostId());
    }

    @Override
    public PostInfoResult getPost(PostParam postParam) {
        PostInfoResult postInfoResult = fandomPostEntityMapper.getPost(postParam);

        return postInfoResult;
    }

    @Override
    public List<PostLikeInfoResult> listPostVotes(PostParam postParam) {
        List<PostLikeInfoResult> list = fandomPostLikeEntityMapper.listPostVotes(postParam.getPostId());
        return list;
    }

    @Override
    public PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn, PageBounds pageBounds) {
        return fandomPostEntityMapper.findPostsOfMyFandoms(memberSn, pageBounds);
    }

    @Override
    public PageList<PostInfoResult> getFriendsPosts(String memberSn, PageBounds pageBounds) {
        return fandomPostEntityMapper.findPostsOfMyFriends(memberSn, pageBounds);
    }


    @Override
    public void addLike(AddLikeParam addLikeParam) {
        FandomPostLikeEntity entity = new FandomPostLikeEntity();
        entity.setPostId(addLikeParam.getPostId());
        entity.setMemberSn(addLikeParam.getMemberSn());
        entity.setLikeTime(new Date());
        this.fandomPostLikeEntityMapper.insert(entity);
    }

    @Override
    public void deleteLike(DeleteLikeParam deleteLikeParam) {
        this.fandomPostLikeEntityMapper.deleteMyLike(deleteLikeParam.getMemberSn(), deleteLikeParam.getPostId());
    }

    @Override
    public PageList<MemberPostInfoResult> getMemberFandomPosts(GetMemberFandomPostsParam getMemberFandomPostsParam) {
        List<MemberPostEntity> memberPostEntities = this.fandomPostEntityMapper.memberFandomPosts(getMemberFandomPostsParam.getFandomId(), getMemberFandomPostsParam.getMemberSn());

        int count = this.fandomPostEntityMapper.countMemberFandomPosts(getMemberFandomPostsParam.getFandomId(), getMemberFandomPostsParam.getMemberSn());

        Paginator paginator = new Paginator(getMemberFandomPostsParam.getPageNum(), getMemberFandomPostsParam.getPageSize(), count);
        PageList<MemberPostInfoResult> memberPostInfoResults = new PageList<>(paginator);

        for (MemberPostEntity entity : memberPostEntities) {
            MemberPostInfoResult memberPostInfoResult = new MemberPostInfoResult();
            memberPostInfoResult.setPostTitle(entity.getPostTitle());
            memberPostInfoResult.setPostContent(entity.getPostContent());
            memberPostInfoResult.setPostNewsfeeds(entity.getPostNewsfeeds());
            memberPostInfoResult.setPostTime(DateFormatUtils.format(entity.getPostTime(), "yyyy-MM-dd HH:mm:ss"));

            MemberPostInfoResult.PostMember postMember = memberPostInfoResult.new PostMember();
            postMember.setSn(entity.getUserEntiy().getSn());
            postMember.setNickname(entity.getUserEntiy().getNickname());
            postMember.setMemberAvatar(entity.getUserEntiy().getMemberAvatar());

            memberPostInfoResult.setPostMember(postMember);

            memberPostInfoResults.add(memberPostInfoResult);
        }

        return memberPostInfoResults;
    }

    @Override
    public PageList<PostInfoResult> searchPosts(SearchPostParam searchPostParam) {
        PageBounds pageBounds = new PageBounds(searchPostParam.getOffset(), searchPostParam.getLimit(), false);
        return fandomPostEntityMapper.searchPosts(searchPostParam.getSearchVal(), pageBounds);
    }
}
