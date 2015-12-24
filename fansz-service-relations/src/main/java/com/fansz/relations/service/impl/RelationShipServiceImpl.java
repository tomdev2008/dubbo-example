package com.fansz.relations.service.impl;

import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.relations.constant.RelationShip;
import com.fansz.relations.entity.UserRelationEntity;
import com.fansz.relations.repository.UserRelationRepository;
import com.fansz.relations.service.RelationShipService;
import com.fansz.service.constant.ErrorCode;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.model.event.SpecialFocusEvent;
import com.fansz.service.model.event.UnSpecialFocusEvent;
import com.fansz.service.model.relationship.AddFriendParam;
import com.fansz.service.model.relationship.FriendInfoResult;
import com.fansz.service.model.relationship.FriendsQueryParam;
import com.fansz.service.model.relationship.OpRequestParam;
import com.fansz.service.model.specialfocus.SpecialFocusParam;
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
    private UserRelationRepository userRelationRepository;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public QueryResult<FriendInfoResult> getFriends(FriendsQueryParam friendsParam, boolean isSpecial) {
        Page p=new Page();
        p.setPage(friendsParam.getPageNum());
        p.setPageSize(friendsParam.getPageSize());
        if (isSpecial) {
            return userRelationRepository.findSpecialFriends(p,friendsParam.getCurrentSn());
        } else {
            return userRelationRepository.findFriends(p,friendsParam.getCurrentSn());
        }
    }

    @Override
    public boolean addFriendRequest(AddFriendParam addFriendParam) {
        UserRelationEntity oldRelation = userRelationRepository.findFriendRelationBySns(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        if (oldRelation != null && !oldRelation.getRelationStatus().equals(RelationShip.TO_ADD.getCode())) {
            throw new ApplicationException(ErrorCode.RELATION_IS_FRIEND, "Is friend already");
        }
        if (oldRelation == null) {
            UserRelationEntity userRelation = BeanTools.copyAs(addFriendParam, UserRelationEntity.class);
            userRelation.setRelationStatus(RelationShip.TO_ADD.getCode());
            userRelationRepository.save(userRelation);

            String myMemberSn = userRelation.getFriendMemberSn();
            String friendMemberSn = userRelation.getMyMemberSn();
            userRelation.setMyMemberSn(myMemberSn);
            userRelation.setFriendMemberSn(friendMemberSn);
            userRelation.setRelationStatus(RelationShip.BE_ADDED.getCode());
            userRelationRepository.save(userRelation);
        }
        return true;
    }

    @Override
    public boolean dealSpecialFriend(AddFriendParam addFriendParam, boolean add) {
        UserRelationEntity oldRelation = userRelationRepository.findFriendRelationBySns(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        SpecialFocusParam specialFocusParam = new SpecialFocusParam();
        specialFocusParam.setCurrentSn(addFriendParam.getCurrentSn());
        specialFocusParam.setSpecialMemberSn(addFriendParam.getFriendMemberSn());
        if (add) {//添加特殊好友
            if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.FRIEND.getCode())) {
                throw new ApplicationException(ErrorCode.RELATION_SPECIAL_NO_ADD, "Can't be special friend");
            }
            oldRelation.setRelationStatus(RelationShip.SPECIAL_FRIEND.getCode());
            eventProducer.produce(AsyncEventType.SPECIAL_FOCUS, new SpecialFocusEvent(specialFocusParam));
        } else {//取消特别好友
            if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.SPECIAL_FRIEND.getCode())) {
                throw new ApplicationException(ErrorCode.RELATION_SPECIAL_NO_DEL, "Can't remove special friend");
            }
            oldRelation.setRelationStatus(RelationShip.FRIEND.getCode());
            eventProducer.produce(AsyncEventType.UN_SPECIAL_FOCUS, new UnSpecialFocusEvent(specialFocusParam));
        }

        userRelationRepository.update(oldRelation);
        return true;
    }

    @Override
    public boolean dealFriendRequest(OpRequestParam opRequestParam, boolean agree) {
        UserRelationEntity oldRelation = userRelationRepository.findRelation(opRequestParam.getCurrentSn(), opRequestParam.getFriendMemberSn());
        if (oldRelation == null || !oldRelation.getRelationStatus().equals(RelationShip.BE_ADDED.getCode())) {
            throw new ApplicationException(ErrorCode.RELATION_FRIEND_NO_EXISTS, "Can't (dis)agree friend");
        }
        userRelationRepository.updateRelationStatus(oldRelation.getId(),RelationShip.FRIEND.getCode());

        oldRelation = userRelationRepository.findRelation(opRequestParam.getFriendMemberSn(), opRequestParam.getCurrentSn());
        userRelationRepository.update(oldRelation);
        return true;
    }

    @Override
    public QueryResult<FriendInfoResult> listAddMeRequest(FriendsQueryParam friendsQueryParam) {
        Page p=new Page();
        p.setPage(friendsQueryParam.getPageNum());
        p.setPageSize(friendsQueryParam.getPageSize());
        return userRelationRepository.listAddMeRequest(p,friendsQueryParam.getCurrentSn());
    }

    @Override
    public QueryResult<FriendInfoResult> listMySendRequest(FriendsQueryParam friendsQueryParam) {
        Page p=new Page();
        p.setPage(friendsQueryParam.getPageNum());
        p.setPageSize(friendsQueryParam.getPageSize());
        return userRelationRepository.listMySendRequest(p,friendsQueryParam.getCurrentSn());
    }

}