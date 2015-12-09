package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.entity.UserRelationEntity;
import com.fansz.members.api.repository.MemberAlbumEntityMapper;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.repository.UserRelationEntityMapper;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.profile.*;
import com.fansz.members.tools.BeanTools;
import com.fansz.members.tools.Constants;
import com.fansz.members.tools.StringTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户配置信息服务实现层
 */
@Service()
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private UserRelationEntityMapper userRelationEntityMapper;

    @Autowired
    private MemberAlbumEntityMapper memberAlbumEntityMapper;

    @Override
    public UserInfoResult getProfile(QueryProfileParam queryUserParam) {
        UserEntity user = userEntityMapper.selectByUid(queryUserParam.getSn());
        UserInfoResult result = BeanTools.copyAs(user, UserInfoResult.class);
        if(StringTools.isNotBlank(queryUserParam.getFriendSn())){
            UserRelationEntity userRelationEntity= userRelationEntityMapper.findRelationBySns(queryUserParam.getSn(),queryUserParam.getFriendSn());
            if(userRelationEntity!=null){
                result.setRelationship(userRelationEntity.getRelationStatus());
            }
        }

        return result;
    }

    @Override
    public void modifyProfile(ModifyProfileParam modifyProfilePara) {
        UserEntity user = BeanTools.copyAs(modifyProfilePara, UserEntity.class);
        user.setProfileUpdatetime(new Date());
        int updated=userEntityMapper.updateByUidSelective(user);
        if(updated!=1){
            throw new ApplicationException(Constants.USER_NOT_FOUND,"User does't exist");
        }
    }

    @Override
    public int setMemberType(SetMemberParam setMemberParam) {
        UserEntity user=new UserEntity();
        user.setSn(setMemberParam.getMemberSn());
        user.setMemberType(setMemberParam.getMemberType());
        return userEntityMapper.updateByUidSelective(user);
    }

    @Override
    public PageList<ContactInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam) {
        PageBounds pageBounds=new PageBounds(contactQueryParam.getOffset(),contactQueryParam.getLimit());
        return userRelationEntityMapper.findRelationByMobiles(contactQueryParam.getMemberSn(), contactQueryParam.getMobileList(), pageBounds);
    }

    @Override
    public PageList<UserInfoResult> searchMembers(UserEntity searchParam,PageBounds pageBounds){
        return userEntityMapper.searchMembers(searchParam.getNickname(),searchParam.getMobile(),searchParam.getMemberType(),searchParam.getLoginname(),pageBounds);
    }
    @Override
    public PageList<UserInfoResult> searchMembers(String searchKey,String sn,PageBounds pageBounds){
        return userEntityMapper.searchMembersByKey(searchKey,sn,pageBounds);
    }
    @Override
    public List<String> getImages(ContactQueryParam contractQueryParam) {
        return memberAlbumEntityMapper.getImages(contractQueryParam.getFriendSn());
    }
}