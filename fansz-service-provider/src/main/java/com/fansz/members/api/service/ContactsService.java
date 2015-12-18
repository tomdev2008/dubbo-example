package com.fansz.members.api.service;

import com.fansz.members.api.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
@Service
public interface ContactsService {

    void addFriend(String id, String followId);

    void removeFriend(String id, String friendId);

    List<UserEntity> getFriends(String id);

    List<UserEntity> findFriend(String uid);

    List<UserEntity> getCandidates(String id);

    void acceptFriend(String id, String followId);

    UserEntity getFriend(String myId, String id);


}
