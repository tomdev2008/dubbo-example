package com.fansz.members.api.service;

import com.fansz.members.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
@Service
public interface ContactsService {

    void addFriend(
            String id,
            String followId) ;

    void removeFriend(
            String id,
            String friendId);

    List<User> getFriends(
            String id);

    List<User> findFriend(
            String id,
            String str);

    List<User> getCandidates(
            String id);

    void acceptFriend(
            String id,
            String followId);

    User getFriend(
            String myId,
            String id);
}
