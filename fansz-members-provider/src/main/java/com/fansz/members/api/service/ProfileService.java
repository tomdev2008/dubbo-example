package com.fansz.members.api.service;

import com.fansz.appservice.persistence.domain.Fandom;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.resource.param.ModifyProfilePara;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface ProfileService {
    User getProfile(String id);

    void modifyProfile(@Valid String id, ModifyProfilePara modifyProfilePara);

    String setAvatar(String userId, FormDataBodyPart filePart) throws IOException;

    List<Fandom> getFollowedFandoms(
            @NotEmpty(message = "error.userId.empty")
            String id);

    List<User> getUsers(
            String s, @NotEmpty(message = "error.mobile.empty")
    String id);
}
