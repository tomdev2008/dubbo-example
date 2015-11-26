package com.fansz.members.api.service.impl;

import com.fansz.appservice.persistence.domain.Friendship;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.persistence.mapper.FriendsMapper;
import com.fansz.appservice.resource.param.CriteriaParam;
import com.fansz.appservice.service.ContactsService;
import com.fansz.appservice.service.ProfileService;
import com.fansz.appservice.utils.Constants;
import com.fansz.appservice.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class ContactsServiceImpl implements ContactsService{

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private ProfileService profileService;

    @Override
    public void addFriend(String id, String followId) {

        Friendship friendship = friendsMapper.getFriend(id, followId);

        if (friendship != null)
        {
            for (User user : friendship.getFriends())
            {
                Assert.isTrue(followId.equals(user.getId()), "error.already.friends");
            }
        }

        User userMe = profileService.getProfile(id);
        User friend = profileService.getProfile(followId);
        friendsMapper.addCandidates(followId, userMe);
        friendsMapper.addRequesters(id, friend);
    }

    @Override
    public void removeFriend(String id, String friendId) {
        User userMe = profileService.getProfile(id);
        User friend = profileService.getProfile(friendId);
        friendsMapper.removeFriend(id, friend);
        friendsMapper.removeFriend(friendId, userMe);
    }

    @Override
    public List<User> getFriends(String id) {
        List<User> friends = friendsMapper.getFriends(id);

        if (null != friends)
        {
            for(User user : friends)
            {
                user.setRelation(Constants.FRIENDS_STATUS_FRIEND);
            }
        }
        return friends;
    }

    @Override
    public List<User> findFriend(String id, CriteriaParam criteria) {

        List<User> users = friendsMapper.findFriend(criteria.getCriteria());

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

    @Override
    public List<User> getCandidates(String id) {

        List<User> users = friendsMapper.getCandidates(id);

        if (users != null && users.size() > 0)
        {
            for (User user : users)
            {
                user.setRelation(Constants.FRIENDS_STATUS_FOLLOW_ME);
            }
        }
        return users;
    }

    @Override
    public void acceptFriend(String id, String followId) {

        Friendship friendship = friendsMapper.getFriend(id, followId);

        if (friendship != null)
        {
            for (User user : friendship.getFriends())
            {
                Assert.isTrue(followId.equals(user.getId()), "error.already.friends");
            }
        }
        User userMe = profileService.getProfile(id);
        User friend = profileService.getProfile(followId);
        friendsMapper.removeCandidates(id, friend);
        friendsMapper.removeRequest(followId, userMe);
        friendsMapper.addFriend(id, friend);
        friendsMapper.addFriend(followId, userMe);
    }

    @Override
    public User getFriend(String myId, String id) {
        User user = profileService.getProfile(id);

        Friendship myFriendShip = friendsMapper.getFriendShip(myId);

        String ship = StringUtils.getShip(id, myFriendShip);

        user.setRelation(ship);

        return user;
    }

}
