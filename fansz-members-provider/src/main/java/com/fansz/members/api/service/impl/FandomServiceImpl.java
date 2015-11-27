package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.FandomFollowEntity;
import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.FandomFollowEntityMapper;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.repository.FandomPostEntityMapper;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.api.service.FileService;
import com.fansz.members.api.utils.StringUtils;
import com.fansz.members.model.Fandom;
import com.fansz.members.model.Post;
import com.fansz.members.model.User;
import com.fansz.members.model.param.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
    private FandomFollowEntityMapper fandomFollowEntityMapper;

    @Override
    public Fandom addFandom(FandomParam fandomParam) throws IOException {

        FandomEntity entity = new FandomEntity();
        entity.setFandomCreatorId(StringUtils.changeIdType(fandomParam.getUserId()));
        entity.setFandomName(fandomParam.getTitle());
        entity.setFandomIntro(fandomParam.getDescription());
        entity.setFandomAvatarUrl(fandomParam.getAvatar());
        entity.setFandomParentId(StringUtils.changeIdType(fandomParam.getCategoryId()));

        //Save fandom
        fandomMapper.insert(entity);

        Fandom fandom = new Fandom(fandomParam);

        return fandom;
    }

    @Override
    public Fandom getFandom(NormalFandomPara fandomPara) {
        FandomEntity entity = fandomMapper.selectByPrimaryKey(fandomPara.getFandomId());
        GetPostsParam param = new GetPostsParam();
        param.setId(fandomPara.getFandomId());
        param.setKind("1");
        List<Post> posts = getPostsByFandom(param);

        Fandom fandom = StringUtils.changeFandom(entity);
        fandom.setPosts(posts);
//        fandom.setFollowed(fandomMapper.isfollowedFandom(user, id));
        return fandom;
    }

    @Override
    public List<Post> getPostsByFandom(GetPostsParam param) {
        List<FandomPostEntity> posts = null;

        if ("1".equals(param.getKind()))
        {
            posts = postEntityMapper.selectHotByFandomId(param.getId());
        }
        else
        {
            posts = postEntityMapper.selectNewByFandomId(param.getId());
        }

        List<Post> postList = new ArrayList<>();

        if (posts != null && posts.size() > 0)
        {
            for (FandomPostEntity entity1 : posts)
            {
                postList.add(StringUtils.changeFandom(entity1));
            }
        }

        return postList;
    }

    @Override
    public List<Fandom> getFandomsByCategoryId(FandomByCategory fandomByCategory) {
        List<FandomEntity> fandoms = fandomMapper.selectByParentId(fandomByCategory.getCategoryId());

//        // 设置我是否已关注这些fandom
//        if (null != fandoms)
//        {
//            User userNew = profileMapper.getProfile(user.getId());
//            if (null != userNew && !StringUtils.isEmpty(userNew.getFandomIds()))
//            {
//                StringUtils.setFollowedFandom(fandoms, userNew.getFandomIds());
//            }
//        }

        return StringUtils.changeFandoms(fandoms);
    }

    /**
     * 关注圈子
     * @param fandomPara 圈子id
     */
    @Override
    public void followFandom(NormalFandomPara fandomPara)
    {
        FandomFollowEntity fandomFollowEntity = new FandomFollowEntity();

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
    public List<Fandom> getRecommendFandom(String id)
    {
        // 获取推荐的fandom
        List<FandomEntity> fandoms = fandomMapper.getRecommendFandom();

//        // 设置我是否已关注这些fandom
//        if (null != fandoms)
//        {
//            User user = profileMapper.getProfile(id);
//            if (null != user && !StringUtils.isEmpty(user.getFandomIds()))
//            {
//                StringUtils.setFollowedFandom(fandoms, user.getFandomIds());
//            }
//        }

        return StringUtils.changeFandoms(fandoms);
    }

    @Override
    public List<User> followerOfFandom(FandomFollowers fandomFollowers) {

//        FandomFollowers pageParam = (FandomFollowers)StringUtils.setPageParam(fandomFollowers);

        List<UserEntity> users = userEntityMapper.getFandomFollowers(fandomFollowers.getId());

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

        return StringUtils.changeUsers(users);
    }


}
