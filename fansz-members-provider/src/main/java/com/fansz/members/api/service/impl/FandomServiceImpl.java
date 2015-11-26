package com.fansz.members.api.service.impl;

import com.fansz.members.api.service.FandomService;
import com.fansz.members.api.utils.StringUtils;
import com.fansz.members.model.fandom.PostsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class FandomServiceImpl implements FandomService {

    @Value("${pictures.base}")
    private String PICTURE_BASH_PATH;

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private FileService fileService;



    @Override
    public Fandom getFandom(Long  user,String id) {
        Fandom fandom = fandomMapper.getFandom(id);
        PostsQueryParam param = new PostsQueryParam();
        param.setId(id);
        param.setKind("1");
        List<Post> posts = getPostsByFandom(param);
        fandom.setPosts(posts);
        fandom.setFollowed(fandomMapper.isfollowedFandom(user, id));
        return fandom;
    }

    @Override
    public List<Post> getPostsByFandom(PostsQueryParam param) {
        return fandomMapper.getPostsByFandomId(param);
    }

    @Override
    public List<Fandom> getFandomsByCategoryId(User user, String categoryId) {
        List<Fandom> fandoms = fandomMapper.getFandomsByCategoryId(categoryId);

        // 设置我是否已关注这些fandom
        if (null != fandoms)
        {
            User userNew = profileMapper.getProfile(user.getId());
            if (null != userNew && !StringUtils.isEmpty(userNew.getFandomIds()))
            {
                StringUtils.setFollowedFandom(fandoms, userNew.getFandomIds());
            }
        }

        return fandoms;
    }

    /**
     * 关注圈子
     * @param user 用户
     * @param id 圈子id
     */
    @Override
    public void followFandom(User user, String id)
    {
        fandomMapper.followFandom(user, id);
        profileMapper.followFandom(user.getId(), id);
    }

    /**
     * 取消关注圈子
     * @param user 用户
     * @param id 圈子id
     */
    @Override
    public void unfollowFandom(User user, String id)
    {
        fandomMapper.unfollowFandom(user, id);
        profileMapper.unFollowFandom(user.getId(), id);
    }

    @Override
    public List<Fandom> getRecommendFandom(String id)
    {
        // 获取推荐的fandom
        List<Fandom> fandoms = fandomMapper.getRecommendFandom();

        // 设置我是否已关注这些fandom
        if (null != fandoms)
        {
            User user = profileMapper.getProfile(id);
            if (null != user && !StringUtils.isEmpty(user.getFandomIds()))
            {
                StringUtils.setFollowedFandom(fandoms, user.getFandomIds());
            }
        }

        return fandoms;
    }

    @Override
    public List<User> followerOfFandom(FandomFollowers fandomFollowers) {

        FandomFollowers pageParam = (FandomFollowers)StringUtils.setPageParam(fandomFollowers);

        List<User> users = fandomMapper.getFandomFollowers(pageParam);

        int size = users.size();
        int start = fandomFollowers.getPageStart();
        int end = fandomFollowers.getPageStart() + fandomFollowers.getCount();

        if (start > size)
        {
            return null;
        }
        else if (start <= size && size <= end)
        {
            return users.subList(start, size);
        }
        else
        {
            return users.subList(start, end);
        }
    }
}
