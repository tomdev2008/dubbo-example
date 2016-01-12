package com.fansz.fandom.service.impl;

import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.fandom.model.profile.*;
import com.fansz.fandom.model.search.SearchMemberParam;
import com.fansz.fandom.repository.MemberAlbumEntityMapper;
import com.fansz.fandom.repository.UserMapper;
import com.fansz.fandom.service.ProfileService;
import com.fansz.fandom.tools.Constants;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.pub.utils.StringTools;
import com.fansz.redis.RelationTemplate;
import com.fansz.redis.UserTemplate;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户配置信息服务实现层
 */
@Service()
@Transactional(propagation = Propagation.REQUIRED)
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserMapper userMapper;

    @Resource(name = "relationTemplate")
    private RelationTemplate relationTemplate;

    @Resource(name = "userTemplate")
    private UserTemplate userTemplate;

    @Autowired
    private MemberAlbumEntityMapper memberAlbumEntityMapper;


    @Override
    public Map<String, String> getProfile(QueryProfileParam queryUserParam) {
        Map<String, String> userMap = userTemplate.get(queryUserParam.getFriendSn());
        if (StringTools.isNotBlank(queryUserParam.getCurrentSn())) {
            String relation = relationTemplate.getRelation(queryUserParam.getCurrentSn(), queryUserParam.getFriendSn());
            String remark = relationTemplate.getFriendRemark(queryUserParam.getCurrentSn(), queryUserParam.getFriendSn());
            userMap.put("relationship", relation);
            userMap.put("remark", remark);
        }

        return userMap;
    }

    @Override
    public void modifyProfile(ModifyProfileParam modifyProfilePara) {
        Map<String, String> user = userTemplate.get(modifyProfilePara.getCurrentSn());
        if (user == null || user.isEmpty()) {
            throw new ApplicationException(Constants.USER_NOT_FOUND, "User does't exist");
        }
        if (StringTools.isNotBlank(modifyProfilePara.getNickname())) {
            boolean exist = isExistsNickname(modifyProfilePara.getNickname(), modifyProfilePara.getCurrentSn());
            if (exist) {
                throw new ApplicationException(Constants.NICK_NAME_REPATEDD, "Nickname repeated");
            }
        }
        Date now = DateTools.getSysDate();
        Map<String, Object> userMap = JsonHelper.convertJSONString2Object(JsonHelper.convertObject2JSONString(modifyProfilePara), Map.class);
        userMap.put("sn", modifyProfilePara.getCurrentSn());
        userMap.put("profile_updatetime", now.getTime());
        userTemplate.updateUser(modifyProfilePara.getCurrentSn(), userMap);

        UserInfoResult userInfoResult = BeanTools.copyAs(modifyProfilePara, UserInfoResult.class);
        userInfoResult.setSn(modifyProfilePara.getCurrentSn());
        userInfoResult.setProfileUpdatetime(now);
        userMapper.updateByUidSelective(userInfoResult);
    }

    @Override
    public boolean isExistsNickname(String nickname, String excludeSn) {
        String userSn = userTemplate.getSnByNickname(nickname);
        return StringTools.isNotBlank(userSn) && !excludeSn.equals(userSn);
    }

    /**
     * 设置会员类别,目前用户信息同时在数据库和redis中保存一份
     *
     * @param setMemberParam
     * @return
     */
    @Override
    public boolean setMemberType(SetMemberParam setMemberParam) {
        Date now = DateTools.getSysDate();
        Map<String, Object> props = new HashMap<>();
        props.put("member_type", setMemberParam.getMemberType());
        props.put("profile_updatetime", now.getTime());
        userTemplate.updateUser(setMemberParam.getCurrentSn(), props);

        UserInfoResult userInfoResult = new UserInfoResult();
        userInfoResult.setMemberType(setMemberParam.getMemberType());
        userInfoResult.setSn(setMemberParam.getCurrentSn());
        userInfoResult.setProfileUpdatetime(now);
        userMapper.updateByUidSelective(userInfoResult);
        return true;
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
            user.setRelationship(relationTemplate.getRelation(sn, user.getSn()));
        }
        return result;
    }

    @Override
    public List<String> getImages(ContactQueryParam contractQueryParam) {
        return memberAlbumEntityMapper.getImages(contractQueryParam.getFriendSn());
    }
}