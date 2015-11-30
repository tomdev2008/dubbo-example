package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.post.GetPostsParam;
import org.springframework.stereotype.Service;

import com.fansz.members.api.entity.FandomMemberEntity;
import com.fansz.members.api.repository.FandomMemberEntityMapper;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.repository.FandomPostEntityMapper;
import com.fansz.members.api.repository.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class FandomServiceImpl implements FandomService {

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private FandomPostEntityMapper postEntityMapper;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private FandomMemberEntityMapper fandomFollowEntityMapper;

    @Override
    public FandomInfoResult addFandom(FandomParam fandomParam) {
        FandomEntity entity = new FandomEntity();
        entity.setFandomCreatorId(Long.valueOf(fandomParam.getUserId()));
        entity.setFandomName(fandomParam.getTitle());
        entity.setFandomIntro(fandomParam.getDescription());
        entity.setFandomAvatarUrl(fandomParam.getAvatar());
        //entity.setFandomParentId(fandomParam.getCategoryId());

        //Save fandom
        fandomMapper.insert(entity);

        FandomInfoResult fandom = new FandomInfoResult();

        return fandom;
    }

    @Override
    public FandomInfoResult getFandom(NormalFandomPara fandomPara) {
        FandomEntity entity = fandomMapper.selectByPrimaryKey(fandomPara.getFandomId());
        GetPostsParam param = new GetPostsParam();
        //param.setId(fandomPara.getFandomId());
        //param.setKind("1");
        //List<Post> posts = getPostsByFandom(param);

        FandomInfoResult fandom = new FandomInfoResult();
        //fandom.setPosts(posts);
//        fandom.setFollowed(fandomMapper.isfollowedFandom(profile, id));
        return fandom;
    }

    @Override
    public List<FandomPostEntity> getPostsByFandom(GetPostsParam param) {
        List<FandomPostEntity> posts = null;

        if ("1".equals(param.getKind()))
        {
            posts = postEntityMapper.selectHotByFandomId(param.getId());
        }
        else
        {
            posts = postEntityMapper.selectNewByFandomId(param.getId());
        }

        /**List<Post> postList = new ArrayList<>();

        if (posts != null && posts.size() > 0)
        {
            for (FandomPostEntity entity1 : posts)
            {
                postList.add(StringUtils.changeFandom(entity1));
            }
        }

        return postList;
         * */
         return posts;
    }

    @Override
    public List<FandomInfoResult> getFandomsByCategoryId(FandomByCategory fandomByCategory) {
        List<FandomEntity> fandoms = fandomMapper.selectByParentId(fandomByCategory.getCategoryId());

//        // 设置我是否已关注这些fandom
//        if (null != fandoms)
//        {
//            User userNew = profileMapper.getProfile(profile.getId());
//            if (null != userNew && !StringUtils.isEmpty(userNew.getFandomIds()))
//            {
//                StringUtils.setFollowedFandom(fandoms, userNew.getFandomIds());
//            }
//        }

        return null;
    }

    /**
     * 关注圈子
     * @param fandomPara 圈子id
     */
    @Override
    public void followFandom(NormalFandomPara fandomPara)
    {
        FandomMemberEntity fandomFollowEntity = new FandomMemberEntity();

        fandomFollowEntity.setMemberId(fandomPara.getUserId());
        fandomFollowEntity.setFandomId(fandomPara.getFandomId());
        fandomFollowEntity.setJoinTime(new Date());

        fandomFollowEntityMapper.insert(fandomFollowEntity);
    }

    /**
     * 取消关注圈子
     * @param id 关注圈子id
     */
    @Override
    public void unfollowFandom(Integer id)
    {
        fandomFollowEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<FandomInfoResult> getRecommendFandom(String id)
    {
        // 获取推荐的fandom
        //List<FandomEntity> fandoms = fandomMapper.getRecommendFandom();

//        // 设置我是否已关注这些fandom
//        if (null != fandoms)
//        {
//            User profile = profileMapper.getProfile(id);
//            if (null != profile && !StringUtils.isEmpty(profile.getFandomIds()))
//            {
//                StringUtils.setFollowedFandom(fandoms, profile.getFandomIds());
//            }
//        }

        return null;
    }

    @Override
    public List<UserEntity> followerOfFandom(FandomFollowers fandomFollowers) {

//        FandomFollowers pageParam = (FandomFollowers)StringUtils.setPageParam(fandomFollowers);

        //List<UserEntity> users = userEntityMapper.getFandomFollowers(fandomFollowers.get());

//        int size = users.size();
//        int start = fandomFollowers.getPageStart();
//        int end = fandomFollowers.getPageStart() + fandomFollowers.getCount();
//
//        if (start > size)
//        {
//            return null;
//        }
//        else if (start <= size && size <= end)
//        {
//            return users.subList(start, size);
//        }
//        else
//        {
//            return users.subList(start, end);
//        }

        //return StringUtils.changeUsers(users);
        return null;
    }


}
