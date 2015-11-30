package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.repository.FandomMemberEntityMapper;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.repository.UserRelationEntityMapper;
import com.fansz.members.api.service.RelationShipService;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by allan on 15/11/29.
 */
@Service
public class RelationShipServiceImpl  implements RelationShipService {

    @Autowired
    private FandomMemberEntityMapper fandomMemberEntityMapper;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private UserRelationEntityMapper userRelationEntityMapper;

    @Override
    public List<FandomInfoResult> findFandomsByUid(String uid) {
        UserEntity user=userEntityMapper.selectByUid(uid);
        List<Long> fandomIds=fandomMemberEntityMapper.findFandomsByUserId(user.getId());
        return fandomMapper.findFandomByIds(fandomIds);
    }

    @Override
    public List<UserInfoResult> getFriends(String uid, PageBounds pageBounds) {
        UserEntity user=userEntityMapper.selectByUid(uid);
        return userRelationEntityMapper.findFriends(user.getId(),pageBounds);
    }
}
