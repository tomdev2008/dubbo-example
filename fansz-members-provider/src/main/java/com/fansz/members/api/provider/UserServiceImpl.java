package com.fansz.members.api.provider;

import com.fansz.members.api.UserService;
import com.fansz.members.model.LoginResult;
import com.fansz.members.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by allan on 15/11/20.
 */
public class UserServiceImpl implements UserService {
    public void register(User user) {

    }

    public LoginResult login(User user) {
        return new LoginResult();
    }

    public void logout(Long userId) {

    }
}
