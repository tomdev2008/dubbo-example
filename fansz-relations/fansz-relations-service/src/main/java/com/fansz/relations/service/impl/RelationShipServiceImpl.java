package com.fansz.relations.service.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.constant.RelationShip;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.db.entity.User;
import com.fansz.db.repository.UserDAO;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.StringTools;
import com.fansz.redis.RelationTemplate;
import com.fansz.redis.UserTemplate;
import com.fansz.redis.model.CountListResult;
import com.fansz.relations.model.*;
import com.fansz.relations.service.RelationShipService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by allan on 16/1/7.
 */
@Component("relationShipService")
public class RelationShipServiceImpl implements RelationShipService {

    @Resource(name = "userTemplate")
    private UserTemplate userTemplate;

    @Resource(name = "relationTemplate")
    private RelationTemplate relationTemplate;

    @Resource(name = "userDAO")
    private UserDAO userDAO;

    /**
     * 查询好友列表
     *
     * @param friendsQueryParam
     * @param isSpecial         如果为true, 查询特殊好友
     * @return
     */
    @Override
    public QueryResult<Map<String, String>> getFriends(final FriendsQueryParam friendsQueryParam, final boolean isSpecial) {
        final int offset = (friendsQueryParam.getPageNum() - 1) * friendsQueryParam.getPageSize();
        final int limit = friendsQueryParam.getPageSize();

        String mySn = friendsQueryParam.getCurrentSn();
        CountListResult<String> snList = null;
        if (isSpecial) {
            snList = relationTemplate.listFriend(mySn, offset, limit);
        } else {
            snList = relationTemplate.listSpecialFriend(mySn, offset, limit);
        }
        if (snList == null || CollectionTools.isNullOrEmpty(snList.getResult())) {
            return new QueryResult<>(new ArrayList<Map<String, String>>(), 0L);
        }
        List<Map<String, String>> friendList = userTemplate.getAll(snList.getResult());
        for (Map<String, String> record : friendList) {
            if (isSpecial) {
                record.put("relationship", RelationShip.SPECIAL_FRIEND.getCode());
            } else {
                record.put("relationship", relationTemplate.getRelation(mySn, record.get("sn")));
            }
        }
        return new QueryResult<>(friendList, snList.getTotalCount());
    }

    /**
     * 发出添加好友请求
     *
     * @param addFriendParam
     * @return
     */
    @Override
    public boolean addFriendRequest(final AddFriendParam addFriendParam) {
        String relation = relationTemplate.getRelation(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        if (!StringTools.isBlank(relation)) {
            throw new ApplicationException(ErrorCode.RELATION_IS_FRIEND);
        }
        return relationTemplate.addFriend(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
    }

    /**
     * 添加或取消特殊关注
     *
     * @param addFriendParam
     * @param add
     * @return
     */
    @Override
    public boolean dealSpecialFriend(final AddFriendParam addFriendParam, final boolean add) {
        String relation = relationTemplate.getRelation(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        if (!StringTools.isBlank(relation)) {
            throw new ApplicationException(ErrorCode.RELATION_IS_FRIEND);
        }
        if (add) {//添加特殊关注,要求是朋友且不能是特殊关注
            if (!RelationShip.FRIEND.getCode().equals(relation)) {
                throw new ApplicationException(ErrorCode.RELATION_SPECIAL_NO_ADD);
            }
            return relationTemplate.addAsSpecial(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        } else {//取消特殊关注,要求已经是特殊好友
            if (!RelationShip.SPECIAL_FRIEND.getCode().equals(relation)) {
                throw new ApplicationException(ErrorCode.RELATION_SPECIAL_NO_DEL);
            }
            return relationTemplate.removeSpecial(addFriendParam.getCurrentSn(), addFriendParam.getFriendMemberSn());
        }
    }

    /**
     * 同意好友请求
     *
     * @param opRequestParam
     * @param agree
     * @return
     */
    @Override
    public boolean dealFriendRequest(final OpRequestParam opRequestParam, boolean agree) {
        String relation = relationTemplate.getRelation(opRequestParam.getCurrentSn(), opRequestParam.getFriendMemberSn());
        if (!RelationShip.BE_ADDED.getCode().equals(relation)) { //检查对方是否在我接收到的好友请求列表中,如果不在,则返回错误消息
            throw new ApplicationException(ErrorCode.RELATION_FRIEND_NO_EXISTS);
        }
        return relationTemplate.agreeFriend(opRequestParam.getCurrentSn(), opRequestParam.getFriendMemberSn());
    }

    /**
     * 分页查询我接收到的好友请求列表,当前为好友\特殊好友关系的数据也返回
     *
     * @param friendsQueryParam
     * @return
     */
    @Override
    public QueryResult<Map<String, String>> listAddMeRequest(final FriendsQueryParam friendsQueryParam) {
        final int offset = (friendsQueryParam.getPageNum() - 1) * friendsQueryParam.getPageSize();
        final int limit = friendsQueryParam.getPageSize();
        String mySn = friendsQueryParam.getCurrentSn();

        CountListResult<String> snList = relationTemplate.listMyReceiveRequest(mySn, offset, limit);
        if (snList == null || CollectionTools.isNullOrEmpty(snList.getResult())) {
            return new QueryResult<>(new ArrayList<Map<String, String>>(), 0L);
        }
        List<Map<String, String>> friendList = userTemplate.getAll(snList.getResult());
        for (Map<String, String> record : friendList) {
            record.put("relationship", relationTemplate.getRelation(mySn, record.get("sn")));
        }
        return new QueryResult<>(friendList, snList.getTotalCount());
    }

    /**
     * 分页查询我主动添加的好友请求,当前为好友\特殊好友关系的数据也返回
     *
     * @param friendsQueryParam
     * @return
     */
    @Override
    public QueryResult<Map<String, String>> listMySendRequest(final FriendsQueryParam friendsQueryParam) {
        final int offset = (friendsQueryParam.getPageNum() - 1) * friendsQueryParam.getPageSize();
        final int limit = friendsQueryParam.getPageSize();
        String mySn = friendsQueryParam.getCurrentSn();

        CountListResult<String> snList = relationTemplate.listMySendRequest(mySn, offset, limit);
        if (snList == null || CollectionTools.isNullOrEmpty(snList.getResult())) {
            return new QueryResult<>(new ArrayList<Map<String, String>>(), 0L);
        }
        List<Map<String, String>> friendList = userTemplate.getAll(snList.getResult());
        for (Map<String, String> record : friendList) {
            record.put("relationship", relationTemplate.getRelation(mySn, record.get("sn")));
        }
        return new QueryResult<>(friendList, snList.getTotalCount());

    }

    @Override
    public QueryResult<FriendInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam) {
        Page p = new Page();
        p.setPage(contactQueryParam.getPageNum());
        p.setPageSize(contactQueryParam.getPageSize());
        QueryResult<User> friendList = userDAO.findByMobiles(p, contactQueryParam.getMobileList());
        return renderResult(contactQueryParam.getCurrentSn(), friendList);
    }

    private QueryResult<FriendInfoResult> renderResult(String currentSn, QueryResult<User> userList) {
        if (CollectionTools.isNullOrEmpty(userList.getResultlist())) {
            return new QueryResult<>(new ArrayList<FriendInfoResult>(), 0);
        }
        List<FriendInfoResult> friendList = new ArrayList<>();
        for (User user : userList.getResultlist()) {
            FriendInfoResult friendInfoResult = BeanTools.copyAs(user, FriendInfoResult.class);
            friendInfoResult.setRelationship(relationTemplate.getRelation(currentSn, friendInfoResult.getSn()));
            friendList.add(friendInfoResult);
        }
        return new QueryResult<>(friendList, userList.getTotalrecord());
    }


    @Override
    public AddContactsRemarkResult addContactsRemark(final AddContactsRemarkParam addContactsRemarkParam) {
        relationTemplate.addFriendRemark(addContactsRemarkParam.getCurrentSn(), addContactsRemarkParam.getFriendMemberSn(), addContactsRemarkParam.getRemark());

        return BeanTools.copyAs(addContactsRemarkParam, AddContactsRemarkResult.class);
    }
}
