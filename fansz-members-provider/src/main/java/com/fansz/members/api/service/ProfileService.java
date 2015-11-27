package com.fansz.members.api.service;

import com.fansz.members.model.Fandom;
import com.fansz.members.model.User;
import com.fansz.members.model.param.ModifyProfilePara;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface ProfileService {
    User getProfile(String id);

    void modifyProfile(String id, ModifyProfilePara modifyProfilePara);

    String setAvatar(String userId, String avatar) throws IOException;

    List<Fandom> getFollowedFandoms(String id);

    List<User> getUsers(String s, String id);
}
