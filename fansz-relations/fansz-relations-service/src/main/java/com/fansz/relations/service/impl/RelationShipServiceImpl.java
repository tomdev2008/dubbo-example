package com.fansz.relations.service.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.db.entity.UserRelation;
import com.fansz.db.model.FriendInfo;
import com.fansz.db.repository.UserRelationDAO;
import com.fansz.event.model.SpecialFocusEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.relations.constant.RelationShip;
import com.fansz.relations.model.*;
import com.fansz.relations.service.RelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan on 15/11/29.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RelationShipServiceImpl implements RelationShipService {

    @Autowired
    private UserRelationDAO userRelationDAO;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public QueryResult<FriendInfoResult> getFriends(FriendsQueryParam friendsParam, boolean isSpecial) {
        Page p = new Page();
        p.setPage(friendsParam.getPageNum());
        p.setPageSize(friendsParam.getPageSize());
        QueryResult<FriendInfo> userList = null;
        if (isSpecial) {
            userList = userRelationDAO.findSpecialFriends(p, friendsParam.getCurrentSn());
        } else {
            userList = userRelationDAO.findFriends(p, friendsParam.getCurrentSn());
        }
        return renderResult(userList);
    }

    @Override
    public boolean addFriendRequest(AddFriendParam addFriendParam) {
        UserRelation oldRelation = userRelationDAO.findFriendRelationBySns(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        if (oldRelation != null && !oldRelation.getRelationStatus().equals(RelationShip.TO_ADD.getCode())) {
            throw new ApplicationException(ErrorCode.RELATION_IS_FRIEND);
        }
        if (oldRelation == null) {
            UserRelation my = BeanTools.copyAs(addFriendParam, UserRelation.class);
            my.setRelationStatus(RelationShip.TO_ADD.getCode());
            userRelationDAO.save(my);

            UserRelation friend = new UserRelation();
            friend.setMyMemberSn(my.getFriendMemberSn());
            friend.setFriendMemberSn(my.getMyMemberSn());
            friend.setRelationStatus(RelationShip.BE_ADDED.getCode());
            userRelationDAO.save(friend);
        }
        return true;
    }

    @Override
    public boolean dealSpecialFriend(AddFriendParam addFriendParam, boolean add) {
        UserRelation oldRelation = userRelationDAO.findFriendRelationBySns(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        SpecialFocusEvent specialFocusEvent = new SpecialFocusEvent();
        specialFocusEvent.setCurrentSn(addFriendParam.getCurrentSn());
        specialFocusEvent.setSpecialMemberSn(addFriendParam.getFriendMemberSn());
        if (add) {//添加特殊好友
            if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.FRIEND.getCode())) {
                throw new ApplicationException(ErrorCode.RELATION_SPECIAL_NO_ADD);
            }
            oldRelation.setRelationStatus(RelationShip.SPECIAL_FRIEND.getCode());
            eventProducer.produce(AsyncEventType.SPECIAL_FOCUS, specialFocusEvent);
        } else {//取消特别好友
            if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.SPECIAL_FRIEND.getCode())) {
                throw new ApplicationException(ErrorCode.RELATION_SPECIAL_NO_DEL);
            }
            oldRelation.setRelationStatus(RelationShip.FRIEND.getCode());
            eventProducer.produce(AsyncEventType.UN_SPECIAL_FOCUS, specialFocusEvent);
        }

        userRelationDAO.update(oldRelation);
        return true;
    }

    @Override
    public boolean dealFriendRequest(OpRequestParam opRequestParam, boolean agree) {
        UserRelation oldRelation = userRelationDAO.findRelation(opRequestParam.getCurrentSn(), opRequestParam.getFriendMemberSn());
        if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.BE_ADDED.getCode())) {
            throw new ApplicationException(ErrorCode.RELATION_FRIEND_NO_EXISTS);
        }
        userRelationDAO.updateRelationStatus(oldRelation.getId(), RelationShip.FRIEND.getCode());

        oldRelation = userRelationDAO.findRelation(opRequestParam.getFriendMemberSn(), opRequestParam.getCurrentSn());
        userRelationDAO.updateRelationStatus(oldRelation.getId(), RelationShip.FRIEND.getCode());
        return true;
    }

    @Override
    public QueryResult<FriendInfoResult> listAddMeRequest(FriendsQueryParam friendsQueryParam) {
        Page p = new Page();
        p.setPage(friendsQueryParam.getPageNum());
        p.setPageSize(friendsQueryParam.getPageSize());
        return renderResult(userRelationDAO.listAddMeRequest(p, friendsQueryParam.getCurrentSn()));
    }

    @Override
    public QueryResult<FriendInfoResult> listMySendRequest(FriendsQueryParam friendsQueryParam) {
        Page p = new Page();
        p.setPage(friendsQueryParam.getPageNum());
        p.setPageSize(friendsQueryParam.getPageSize());
        return renderResult(userRelationDAO.listMySendRequest(p, friendsQueryParam.getCurrentSn()));
    }

    private QueryResult<FriendInfoResult> renderResult(QueryResult<FriendInfo> userList) {
        if (CollectionTools.isNullOrEmpty(userList.getResultlist())) {
            return new QueryResult<>(new ArrayList<FriendInfoResult>(), 0);
        }
        List<FriendInfoResult> friendList = BeanTools.copyAs(userList.getResultlist(), FriendInfoResult.class);
        return new QueryResult<>(friendList, userList.getTotalrecord());
    }

    @Override
    public QueryResult<FriendInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam) {
        Page p = new Page();
        p.setPage(contactQueryParam.getPageNum());
        p.setPageSize(contactQueryParam.getPageSize());
        QueryResult<FriendInfo> friendList = userRelationDAO.findRelationByMobiles(p, contactQueryParam.getCurrentSn(), contactQueryParam.getMobileList());
        return renderResult(friendList);
    }
}