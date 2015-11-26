package com.fansz.members.api.service;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface ContactsService {

    void addFriend(
            @NotEmpty(message = "error.userId.empty")
            @Length(max = 50, message = "error.userId.over")
            String id,
            @NotEmpty(message = "error.followId.empty")
            @Length(max = 50, message = "error.followId.over")
            String followId) ;

    void removeFriend(
            @NotEmpty(message = "error.userId.empty")
            @Length(max = 50, message = "error.userId.over")
            String id,
            @NotEmpty(message = "error.friendId.empty")
            @Length(max = 50, message = "error.friendId.over")
            String friendId);

    List<User> getFriends(
            @NotEmpty(message = "error.userId.empty")
            @Length(max = 50, message = "error.userId.over")
            String id);

    List<User> findFriend(
            @NotEmpty(message = "error.userId.empty")
            @Length(max = 50, message = "error.userId.over")
            String id,
            @Valid
            CriteriaParam criteria);

    List<User> getCandidates(
            @NotEmpty(message = "error.followId.empty")
            @Length(max = 50, message = "error.followId.over")
            String id);

    void acceptFriend(
            @NotEmpty(message = "error.userId.empty")
            @Length(max = 50, message = "error.userId.over")
            String id,
            @NotEmpty(message = "error.followId.empty")
            @Length(max = 50, message = "error.followId.over")
            String followId);

    User getFriend(
            @NotEmpty(message = "error.userId.empty")
            @Length(max = 50, message = "error.userId.over")
            String myId,
            @NotEmpty(message = "error.friendId.empty")
            @Length(max = 50, message = "error.friendId.over")
            String id);
}
