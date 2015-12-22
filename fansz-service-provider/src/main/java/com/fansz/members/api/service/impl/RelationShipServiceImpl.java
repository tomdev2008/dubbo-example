package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserRelationEntity;
import com.fansz.members.api.repository.UserRelationEntityMapper;
import com.fansz.members.api.service.RelationShipService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.kafka.EventProducer;
import com.fansz.members.model.event.AsyncEventType;
import com.fansz.members.model.event.SpecialFocusEvent;
import com.fansz.members.model.event.UnSpecialFocusEvent;
import com.fansz.members.model.relationship.AddFriendParam;
import com.fansz.members.model.relationship.FriendInfoResult;
import com.fansz.members.model.relationship.FriendsQueryParam;
import com.fansz.members.model.relationship.OpRequestParam;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import com.fansz.members.tools.Constants;
import com.fansz.members.tools.RelationShip;
import com.fansz.pub.utils.BeanTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by allan on 15/11/29.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RelationShipServiceImpl implements RelationShipService {

    @Autowired
    private UserRelationEntityMapper userRelationEntityMapper;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public PageList<FriendInfoResult> getFriends(String uid, PageBounds pageBounds, boolean isSpecial) {
        if (isSpecial) {
            return userRelationEntityMapper.findSpecialFriends(uid, pageBounds);
        } else {
            return userRelationEntityMapper.findFriends(uid, pageBounds);
        }
    }

    @Override
    public boolean addFriendRequest(AddFriendParam addFriendParam) {
        UserRelationEntity oldRelation = userRelationEntityMapper.findFriendRelationBySns(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        if (oldRelation != null && !oldRelation.getRelationStatus().equals(RelationShip.TO_ADD.getCode())) {
            throw new ApplicationException(Constants.RELATION_IS_FRIEND, "Is friend already");
        }
        if (oldRelation == null) {
            UserRelationEntity userRelation = BeanTools.copyAs(addFriendParam, UserRelationEntity.class);
            userRelation.setRelationStatus(RelationShip.TO_ADD.getCode());
            userRelationEntityMapper.insert(userRelation);

            String myMemberSn = userRelation.getFriendMemberSn();
            String friendMemberSn = userRelation.getMyMemberSn();
            userRelation.setMyMemberSn(myMemberSn);
            userRelation.setFriendMemberSn(friendMemberSn);
            userRelation.setRelationStatus(RelationShip.BE_ADDED.getCode());
            userRelationEntityMapper.insert(userRelation);
        }
        return true;
    }

    @Override
    public boolean dealSpecialFriend(AddFriendParam addFriendParam, boolean add) {
        UserRelationEntity oldRelation = userRelationEntityMapper.findFriendRelationBySns(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        SpecialFocusParam specialFocusParam = new SpecialFocusParam();
        specialFocusParam.setCurrentSn(addFriendParam.getCurrentSn());
        specialFocusParam.setSpecialMemberSn(addFriendParam.getFriendMemberSn());
        if (add) {//添加特殊好友
            if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.FRIEND.getCode())) {
                throw new ApplicationException(Constants.RELATION_SPECIAL_NO_ADD, "Can't be special friend");
            }
            oldRelation.setRelationStatus(RelationShip.SPECIAL_FRIEND.getCode());
            eventProducer.produce(AsyncEventType.SPECIAL_FOCUS, new SpecialFocusEvent(specialFocusParam));
        } else {//取消特别好友
            if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.SPECIAL_FRIEND.getCode())) {
                throw new ApplicationException(Constants.RELATION_SPECIAL_NO_DEL, "Can't remove special friend");
            }
            oldRelation.setRelationStatus(RelationShip.FRIEND.getCode());
            eventProducer.produce(AsyncEventType.UN_SPECIAL_FOCUS, new UnSpecialFocusEvent(specialFocusParam));
        }

        userRelationEntityMapper.updateByPrimaryKeySelective(oldRelation);
        return true;
    }

    @Override
    public boolean dealFriendRequest(OpRequestParam opRequestParam, boolean agree) {
        UserRelationEntity oldRelation = userRelationEntityMapper.findRelation(opRequestParam.getCurrentSn(), opRequestParam.getFriendMemberSn());
        if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.BE_ADDED.getCode())) {
            throw new ApplicationException(Constants.RELATION_FRIEND_NO_EXISTS, "Can't (dis)agree friend");
        }
        oldRelation.setRelationStatus(RelationShip.FRIEND.getCode());
        userRelationEntityMapper.updateByPrimaryKeySelective(oldRelation);

        oldRelation = userRelationEntityMapper.findRelation(opRequestParam.getFriendMemberSn(), opRequestParam.getCurrentSn());
        oldRelation.setRelationStatus(RelationShip.FRIEND.getCode());
        userRelationEntityMapper.updateByPrimaryKeySelective(oldRelation);
        return true;
    }

    @Override
    public PageList<FriendInfoResult> listAddMeRequest(FriendsQueryParam friendsQueryParam) {
        PageBounds pageBounds = new PageBounds(friendsQueryParam.getOffset(), friendsQueryParam.getLimit());
        return userRelationEntityMapper.listAddMeRequest(friendsQueryParam.getCurrentSn(), pageBounds);
    }

    @Override
    public PageList<FriendInfoResult> listMySendRequest(FriendsQueryParam friendsQueryParam) {
        PageBounds pageBounds = new PageBounds(friendsQueryParam.getOffset(), friendsQueryParam.getLimit());
        return userRelationEntityMapper.listMySendRequest(friendsQueryParam.getCurrentSn(), pageBounds);
    }

}