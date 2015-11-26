package com.fansz.members.api.service;

import com.fansz.members.model.User;
import com.fansz.members.model.param.CriteriaParam;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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
            CriteriaParam criteria);

    List<User> getCandidates(
            String id);

    void acceptFriend(
            String id,
            String followId);

    User getFriend(
            String myId,
            String id);
}
