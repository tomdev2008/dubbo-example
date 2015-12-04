package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.MemberAlbumEntityMapper;
import com.fansz.members.api.service.ContactsService;
import com.fansz.members.model.profile.ContactQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    MemberAlbumEntityMapper memberAlbumEntityMapper;

    @Override
    public void addFriend(String id, String followId) {

    }

    @Override
    public void removeFriend(String id, String friendId) {

    }

    @Override
    public List<UserEntity> getFriends(String id) {
        return null;
    }

    @Override
    public List<UserEntity> findFriend(String uid) {
        return null;
    }

    @Override
    public List<UserEntity> getCandidates(String id) {
        return null;
    }

    @Override
    public void acceptFriend(String id, String followId) {

    }

    @Override
    public UserEntity getFriend(String myId, String id) {
        return null;
    }

    @Override
    public List<String> getImages(ContactQueryParam contractQueryParam) {
        return memberAlbumEntityMapper.getImages(contractQueryParam.getFriendSn());
    }

}
