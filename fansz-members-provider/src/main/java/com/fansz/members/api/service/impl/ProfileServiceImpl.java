package com.fansz.members.api.service.impl;

import com.fansz.members.api.service.ProfileService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.api.utils.StringUtils;
import com.fansz.members.model.Fandom;
import com.fansz.members.model.Friendship;
import com.fansz.members.model.User;
import com.fansz.members.model.param.ModifyProfilePara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Value("${pictures.base}")
    private String PICTURE_BASE_PATH;

//    @Autowired
//    private ProfileMapper profileMapper;
//
//    @Autowired
//    private FandomMapper fandomMapper;
//
//    @Autowired
//    private FileService fileService;
//
//    @Autowired
//    private FriendsMapper friendsMapper;

    @Override
    public User getProfile(String id) {

//        return profileMapper.getProfile(id);
        return null;
    }

    @Override
    public void modifyProfile(String id, ModifyProfilePara modifyProfilePara) {
//        profileMapper.modifyProfile(id, modifyProfilePara);
    }

    @Override
    public String setAvatar(String userId, String avatar) throws IOException {

        //保存头像文件名
//        profileMapper.saveAvatar(userId, avatar);

        return avatar;
    }

    @Override
    public List<Fandom> getFollowedFandoms(String id) {
//        User user = profileMapper.getProfile(id);
//
//        if (null != user && !StringUtils.isEmpty(user.getFandomIds()))
//        {
//            List<String> fandomsId = user.getFandomIds();
//            List<Fandom> fandoms = fandomMapper.getFandomsByIds(fandomsId);
//
//            if (fandoms != null && fandoms.size() > 0)
//            {
//                for (Fandom fandom : fandoms)
//                {
//                    fandom.setFollowed("1");
//                }
//            }
//
//            return fandoms;
//        }
//        else
//        {
//            return null;
//        }
        return null;
    }

    @Override
    public List<User> getUsers(String id, String mobile) {
        String[] mobiles = mobile.split(",");
        List<String> mobileList = Arrays.asList(mobiles);
        List<User> users = null;
//        List<User> users = List<User> users = profileMapper.getUsers(mobileList);

//        Friendship myFriendship = friendsMapper.getFriendShip(id);
//
//        if (null != users)
//        {
//            for (User user : users)
//            {
//                user.setRelation(StringUtils.getShip(user.getId(), myFriendship));
//
//                if (id.equals(user.getId()))
//                {
//                    user.setRelation(Constants.FRIENDS_STATUS_MYSELF);
//                }
//            }
//        }
        return users;
    }
}
