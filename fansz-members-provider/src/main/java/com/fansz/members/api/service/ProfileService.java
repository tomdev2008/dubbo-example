package com.fansz.members.api.service;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.friendship.FocusedFandomResult;
import com.fansz.members.model.user.FriendResult;
import com.fansz.members.model.user.ModifyProfileParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface ProfileService {
    UserEntity getProfile(Long uid);

    void modifyProfile(Long id, ModifyProfileParam modifyProfileParam);

    String setAvatar(String userId, InputStream filePart) throws IOException;

    List<FocusedFandomResult> getFollowedFandoms(Long uid);

    List<FriendResult> getFriends(Long uid);
}
