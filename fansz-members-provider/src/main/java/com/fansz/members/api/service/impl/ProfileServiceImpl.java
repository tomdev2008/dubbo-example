package com.fansz.members.api.service.impl;

import com.fansz.appservice.persistence.domain.Fandom;
import com.fansz.appservice.persistence.domain.Friendship;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.persistence.mapper.FandomMapper;
import com.fansz.appservice.persistence.mapper.FriendsMapper;
import com.fansz.appservice.persistence.mapper.ProfileMapper;
import com.fansz.appservice.resource.param.ModifyProfilePara;
import com.fansz.appservice.service.FileService;
import com.fansz.appservice.service.ProfileService;
import com.fansz.appservice.utils.Constants;
import com.fansz.appservice.utils.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
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

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private FriendsMapper friendsMapper;

    @Override
    public User getProfile(String id) {

        return profileMapper.getProfile(id);
    }

    @Override
    public void modifyProfile(String id, ModifyProfilePara modifyProfilePara) {
        profileMapper.modifyProfile(id, modifyProfilePara);
    }

    @Override
    public String setAvatar(String userId, FormDataBodyPart filePart) throws IOException {

        //获取文件IO流
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);

        //获取文件名
        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
        String source = formDataContentDisposition.getFileName();
        source = new String(source.getBytes("ISO8859-1"), "UTF-8");

        //写文件流
        String fileName = fileService.fileUpload(PICTURE_BASE_PATH, fileInputStream, source);

        //保存头像文件名
        profileMapper.saveAvatar(userId, fileName);

        return fileName;
    }

    @Override
    public List<Fandom> getFollowedFandoms(String id) {
        User user = profileMapper.getProfile(id);

        if (null != user && !StringUtils.isEmpty(user.getFandomIds()))
        {
            List<String> fandomsId = user.getFandomIds();
            List<Fandom> fandoms = fandomMapper.getFandomsByIds(fandomsId);

            if (fandoms != null && fandoms.size() > 0)
            {
                for (Fandom fandom : fandoms)
                {
                    fandom.setFollowed("1");
                }
            }

            return fandoms;
        }
        else
        {
            return null;
        }

    }

    @Override
    public List<User> getUsers(String id, String mobile) {
        String[] mobiles = mobile.split(",");
        List<String> mobileList = Arrays.asList(mobiles);

        List<User> users = profileMapper.getUsers(mobileList);

        Friendship myFriendship = friendsMapper.getFriendShip(id);

        if (null != users)
        {
            for (User user : users)
            {
                user.setRelation(StringUtils.getShip(user.getId(), myFriendship));

                if (id.equals(user.getId()))
                {
                    user.setRelation(Constants.FRIENDS_STATUS_MYSELF);
                }
            }
        }
        return users;
    }
}
