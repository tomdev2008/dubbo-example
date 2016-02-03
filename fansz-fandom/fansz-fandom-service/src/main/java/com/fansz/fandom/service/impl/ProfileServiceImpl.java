package com.fansz.fandom.service.impl;

import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.fandom.model.profile.*;
import com.fansz.fandom.model.search.SearchMemberParam;
import com.fansz.fandom.repository.MemberAlbumEntityMapper;
import com.fansz.fandom.repository.UserMapper;
import com.fansz.fandom.service.AsyncEventService;
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
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Resource(name = "relationTemplate")
    private RelationTemplate relationTemplate;

    @Resource(name = "userTemplate")
    private UserTemplate userTemplate;

    @Autowired
    private MemberAlbumEntityMapper memberAlbumEntityMapper;

    @Autowired
    private AsyncEventService asyncEventService;

    @Resource(name = "searchClient")
    private Client searchClient;

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

        asyncEventService.onUserChanged(userMap);
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

        props.put("sn", setMemberParam.getCurrentSn());
        asyncEventService.onUserChanged(props);
        return true;
    }


    @Override
    public PageList<UserInfoResult> searchMembers(SearchMemberParam searchMemberParam) {
        PageBounds pageBounds = new PageBounds(searchMemberParam.getPageNum(), searchMemberParam.getPageSize());
        PageList<UserInfoResult> result = search(null, searchMemberParam.getMemberType(), pageBounds);
        for (UserInfoResult user : result) {
            user.setRelationship(relationTemplate.getRelation(searchMemberParam.getCurrentSn(), user.getSn()));
        }
        return result;
    }

    @Override
    public PageList<UserInfoResult> searchMembers(String searchKey, String sn, PageBounds pageBounds) {
        PageList<UserInfoResult> result = search(searchKey, null, pageBounds);
        for (UserInfoResult user : result) {
            user.setRelationship(relationTemplate.getRelation(sn, user.getSn()));
        }
        return result;
    }

    @Override
    public List<String> getImages(ContactQueryParam contractQueryParam) {
        return memberAlbumEntityMapper.getImages(contractQueryParam.getFriendSn());
    }

    public PageList<UserInfoResult> search(String searchKey, String searchType, PageBounds pageBounds) {
        SearchRequestBuilder builder = searchClient.prepareSearch("fansz").setTypes("user").setSearchType(SearchType.DEFAULT).setFrom(pageBounds.getOffset()).setSize(pageBounds.getLimit());
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (StringTools.isNotBlank(searchKey)) {
            qb.should(new QueryStringQueryBuilder(searchKey).field("mobile").field("loginname").field("nickname"));
        } else if (StringTools.isNotBlank(searchType)) {
            qb.must(new QueryStringQueryBuilder(searchType).field("member_type"));
        }
        builder.setQuery(qb);
        SearchResponse response = builder.execute().actionGet();
        SearchHits hits = response.getHits();
        logger.debug("查询到记录数={}", hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        PageList<UserInfoResult> userList = new PageList<>(new Paginator(pageBounds.getPage(), pageBounds.getLimit(), (int) hits.getTotalHits()));
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                Map<String, Object> props = hit.getSource();
                userList.add(JsonHelper.copyAs(props, UserInfoResult.class));
            }
        }
        return userList;
    }
}