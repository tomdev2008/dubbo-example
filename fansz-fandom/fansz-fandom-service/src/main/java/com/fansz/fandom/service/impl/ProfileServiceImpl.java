package com.fansz.fandom.service.impl;

import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.fandom.entity.UserEntity;
import com.fansz.fandom.model.profile.*;
import com.fansz.fandom.model.search.SearchMemberParam;
import com.fansz.fandom.repository.MemberAlbumEntityMapper;
import com.fansz.fandom.repository.UserMapper;
import com.fansz.fandom.service.ProfileService;
import com.fansz.fandom.service.RelationService;
import com.fansz.fandom.tools.Constants;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.StringTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户配置信息服务实现层
 */
@Service()
@Transactional(propagation = Propagation.REQUIRED)
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserMapper userMapper;

    @Resource(name = "relationService")
    private RelationService relationService;

    @Autowired
    private MemberAlbumEntityMapper memberAlbumEntityMapper;

    @Override
    public UserInfoResult getProfile(QueryProfileParam queryUserParam) {
        UserEntity user = userMapper.selectByUid(queryUserParam.getFriendSn());
        UserInfoResult result = BeanTools.copyAs(user, UserInfoResult.class);
        if (StringTools.isNotBlank(queryUserParam.getCurrentSn())) {
            String relation = relationService.getRelation(queryUserParam.getCurrentSn(), queryUserParam.getFriendSn());
            result.setRelationship(relation);
        }

        return result;
    }

    @Override
    public void modifyProfile(ModifyProfileParam modifyProfilePara) {
        if (StringTools.isNotBlank(modifyProfilePara.getNickname())) {
            int total = isExistsNickname(modifyProfilePara.getNickname(), modifyProfilePara.getCurrentSn());
            if (total > 0) {
                throw new ApplicationException(Constants.NICK_NAME_REPATEDD, "Nickname repeated");
            }
        }

        UserEntity user = BeanTools.copyAs(modifyProfilePara, UserEntity.class);
        user.setSn(modifyProfilePara.getCurrentSn());
        user.setProfileUpdatetime(new Date());
        int updated = userMapper.updateByUidSelective(user);
        if (updated != 1) {
            throw new ApplicationException(Constants.USER_NOT_FOUND, "User does't exist");
        }
    }

    @Override
    public int isExistsNickname(String nickname, String excludeSn) {
        return userMapper.isExistsNickname(nickname, excludeSn);
    }

    @Override
    public int setMemberType(SetMemberParam setMemberParam) {
        UserEntity user = new UserEntity();
        user.setSn(setMemberParam.getCurrentSn());
        user.setMemberType(setMemberParam.getMemberType());
        return userMapper.updateByUidSelective(user);
    }


    @Override
    public PageList<UserInfoResult> searchMembers(SearchMemberParam searchMemberParam) {
        PageBounds pageBounds = new PageBounds(searchMemberParam.getPageNum(), searchMemberParam.getPageSize());
        return userMapper.searchMembers(null, null, searchMemberParam.getMemberType(), null, pageBounds);
    }

    @Override
    public PageList<UserInfoResult> searchMembers(String searchKey, String sn, PageBounds pageBounds) {
        PageList<UserInfoResult> result = userMapper.searchMembersByKey(searchKey, sn, pageBounds);
        for (UserInfoResult user : result) {
            user.setRelationship(relationService.getRelation(sn, user.getSn()));
        }
        return result;
    }

    @Override
    public List<String> getImages(ContactQueryParam contractQueryParam) {
        return memberAlbumEntityMapper.getImages(contractQueryParam.getFriendSn());
    }
}