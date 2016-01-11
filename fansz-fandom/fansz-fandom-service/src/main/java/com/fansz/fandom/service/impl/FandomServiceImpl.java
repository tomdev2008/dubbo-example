package com.fansz.fandom.service.impl;

import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.event.model.SpecialFocusEvent;
import com.fansz.event.model.UnSpecialFocusEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.fandom.entity.FandomEntity;
import com.fansz.fandom.entity.FandomMemberEntity;
import com.fansz.fandom.model.fandom.*;
import com.fansz.fandom.model.profile.ContactInfoResult;
import com.fansz.fandom.model.relationship.ExitFandomParam;
import com.fansz.fandom.model.relationship.JoinFandomsParam;
import com.fansz.fandom.repository.FandomMapper;
import com.fansz.fandom.repository.FandomMemberEntityMapper;
import com.fansz.fandom.repository.FandomTagMapper;
import com.fansz.fandom.service.FandomService;
import com.fansz.fandom.tools.Constants;
import com.fansz.pub.utils.*;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.*;


/**
 * fandom服务实现类
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FandomServiceImpl implements FandomService {

    //fandom分类在redis存储中的key前缀
    public static final String CATEGORY_PREFIX = "fandomCategory:";

    //fandom所有一二级分类的id列表的key
    public static final String CATEGORY_LIST_KEY = "fandomCategoryList";

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private FandomMemberEntityMapper fandomMemberEntityMapper;

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    @Autowired
    private FandomTagMapper fandomTagMapper;

    @Autowired
    private JedisTemplate jedisTemplate;

    /**
     * 根据前台传入参数,查询符合条件的fandom列表
     *
     * @param fandomQueryParam
     * @return
     */
    @Override
    public List<FandomInfoResult> listFandom(FandomQueryParam fandomQueryParam) {
        FandomEntity param = BeanTools.copyAs(fandomQueryParam, FandomEntity.class);
        return fandomMapper.listByCondition(param);
    }

    @Override
    public PageList<FandomInfoResult> findFandomsByMemberSn(String sn, PageBounds pageBounds) {
        return fandomMemberEntityMapper.findFandomsByMemberSn(sn, pageBounds);
    }

    @Override
    public boolean joinFandom(JoinFandomsParam joinFandomsParam) {
        String memberSn = joinFandomsParam.getCurrentSn();
        Date now = DateTools.getSysDate();
        for (String fandomId : joinFandomsParam.getFandomIds()) {
            FandomMemberEntity fandomMember = new FandomMemberEntity();
            fandomMember.setMemberSn(memberSn);
            fandomMember.setFandomId(fandomId);
            fandomMember.setJoinTime(now);
            fandomMember.setInfatuation("1");//1表示特别关注
            FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(fandomMember);
            if (exist != null) {
                throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is already in fandom");
            }

            fandomMemberEntityMapper.insertSelective(fandomMember);

            //添加特别关注记录
            eventProducer.produce(AsyncEventType.SPECIAL_FOCUS, new SpecialFocusEvent(memberSn, null, Long.parseLong(fandomId), null));
        }
        return true;
    }


    @Override
    public boolean exitFandom(ExitFandomParam joinFandomParam) {
        FandomMemberEntity queryParam = BeanTools.copyAs(joinFandomParam, FandomMemberEntity.class);
        queryParam.setMemberSn(joinFandomParam.getCurrentSn());
        FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(queryParam);
        if (exist == null) {
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is not in fandom");
        }
        fandomMemberEntityMapper.deleteByPrimaryKey(exist.getId());

        //删除特别关注记录
        UnSpecialFocusEvent unSpecialFocusEvent = new UnSpecialFocusEvent();
        unSpecialFocusEvent.setCurrentSn(joinFandomParam.getCurrentSn());
        unSpecialFocusEvent.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));

        eventProducer.produce(AsyncEventType.UN_SPECIAL_FOCUS, unSpecialFocusEvent);
        return false;
    }


    public PageList<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam) {
        PageBounds pageBounds = new PageBounds(fandomQueryParam.getPageNum(), fandomQueryParam.getPageSize());
        return fandomMapper.getRecommendFandom(fandomQueryParam.getCurrentSn(), pageBounds);
    }

    @Override
    public List<Map<String, Object>> getFandomCategory(FandomQueryParam fandomQueryParam) {
        List<Map<String, Object>> rootCategoryList = new ArrayList<>();
        List<Map<String, String>> categoryFandoms = jedisTemplate.execute(new JedisCallback<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInRedis(Jedis jedis) throws Exception {
                List<Map<String, String>> categoryList = new ArrayList<>();
                Long size = jedis.llen(CATEGORY_LIST_KEY);
                if (size > 0) {
                    List<String> categoryIds = jedis.lrange(CATEGORY_LIST_KEY, 0, size - 1);
                    for (String categoryId : categoryIds) {
                        Map<String, String> categoryMap = jedis.hgetAll(CATEGORY_PREFIX + categoryId);
                        categoryList.add(categoryMap);
                    }
                }
                return categoryList;
            }
        });
        //find root category
        for (Map<String, String> rootCategory : categoryFandoms) {
            if ("0".equals(rootCategory.get("fandom_parent_id"))) {
                Map<String, Object> categoryMap = new HashMap<>();
                categoryMap.put("id", rootCategory.get("id"));
                categoryMap.put("fandom_name", rootCategory.get("fandom_name"));
                categoryMap.put("fandom_parent_id", rootCategory.get("fandom_parent_id"));
                categoryMap.put("fandom_avatar_url", rootCategory.get("fandom_avatar_url"));
                categoryMap.put("fandom_intro", rootCategory.get("fandom_intro"));
                categoryMap.put("fandom_create_time", rootCategory.get("fandom_create_time"));
                rootCategoryList.add(categoryMap);
            }
        }
        //set child category
        for (Map<String, String> childCategory : categoryFandoms) {
            if (!"0".equals(childCategory.get("fandom_parent_id"))) {
                Map<String, Object> rootCategory = CollectionTools.find(rootCategoryList, "id", childCategory.get("fandom_parent_id"));
                if (rootCategory != null) {
                    if (CollectionTools.isNullOrEmpty((List) rootCategory.get("child"))) {
                        List<Map<String, String>> childList = new ArrayList<>();
                        childList.add(childCategory);
                        rootCategory.put("child", childList);
                    } else {
                        List<Map<String, String>> childList = (List<Map<String, String>>) rootCategory.get("child");
                        childList.add(childCategory);
                        rootCategory.put("child", childList);
                    }
                }
            }
        }
        //如果从redis中没有查到category,从数据库中查找
        if (rootCategoryList.size() == 0) {
            List<Map<String, Object>> rootCategory = fandomMapper.getFandomCategoryMap(0L);
            if (!CollectionTools.isNullOrEmpty(rootCategory)) {
                for (Map<String, Object> root : rootCategory) {
                    List<Map<String, Object>> childCategory = fandomMapper.getFandomCategoryMap(((Integer) root.get("id")).longValue());
                    final Map<String, String> rootCategoryMap = assemblyFandomCreator(root);
                    final List<Map<String, String>> childCategoryMapList = new ArrayList<>();
                    for (Map<String, Object> category : childCategory) {
                        Map<String, String> categoryMap = assemblyFandomCreator(category);
                        childCategoryMapList.add(categoryMap);
                    }
                    jedisTemplate.execute(new JedisCallback<Object>() {
                        @Override
                        public Object doInRedis(Jedis jedis) throws Exception {
                            Pipeline pipe = jedis.pipelined();
                            try {
                                pipe.lpush(CATEGORY_LIST_KEY, rootCategoryMap.get("id"));
                                pipe.hmset(CATEGORY_PREFIX + rootCategoryMap.get("id"), rootCategoryMap);
                                for (Map<String, String> categoryMap : childCategoryMapList) {
                                    pipe.lpush(CATEGORY_LIST_KEY, categoryMap.get("id"));
                                    pipe.hmset(CATEGORY_PREFIX + categoryMap.get("id"), categoryMap);
                                }
                            } finally {
                                if (pipe != null) {
                                    pipe.close();
                                }
                            }
                            return null;
                        }
                    });
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.putAll(rootCategoryMap);
                    resultMap.put("child", childCategoryMapList);
                    rootCategoryList.add(resultMap);
                }
            }
        }
        return rootCategoryList;
    }

    private Map<String, String> assemblyFandomCreator(Map<String, Object> fandomMap) {
        Map<String, String> resultMap = new HashMap<>();
        Map<String, String> userMap = null;
        for (Map.Entry<String, Object> entry : fandomMap.entrySet()) {
            if (entry.getKey().startsWith("u_")) {
                if (userMap == null) {
                    userMap = new HashMap<>();
                }
                if (entry.getValue() instanceof Date) {
                    userMap.put(entry.getKey().substring(2), ((Date) entry.getValue()).getTime() + "");
                } else {
                    userMap.put(entry.getKey().substring(2), String.valueOf(entry.getValue()));
                }
            } else {
                if (entry.getValue() instanceof Date) {
                    resultMap.put(entry.getKey(), ((Date) entry.getValue()).getTime() + "");
                } else {
                    resultMap.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        if (userMap != null) {
            resultMap.put("creator", JsonHelper.convertMapToJSONString(userMap));
        }
        return resultMap;
    }

    @Override
    public PageList<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam) {
        PageBounds pageBounds = new PageBounds(fandomQueryParam.getPageNum(), fandomQueryParam.getPageSize());
        return fandomMemberEntityMapper.getFandomMembers(fandomQueryParam.getFandomId(), fandomQueryParam.getCurrentSn(), pageBounds);
    }

    public FandomInfoResult getFandomInfo(FandomInfoParam fandomInfoParam) {
        FandomInfoResult fandomInfoResult = fandomMapper.getFandomDetail(fandomInfoParam.getFandomId(), fandomInfoParam.getCurrentSn());
        if (null != fandomInfoResult) {
            List<FandomTagResult> fandomTagList = fandomTagMapper.selectFandomTagsByFandomId(fandomInfoParam.getFandomId());
            fandomInfoResult.setFandomTagResultList(fandomTagList);
        }
        return fandomInfoResult;
    }

    @Override
    public PageList<FandomInfoResult> searchFandoms(SearchFandomParam searchFandomParam) {
        PageBounds pageBounds = new PageBounds(searchFandomParam.getPageNum(), searchFandomParam.getPageSize());
        return fandomMapper.searchFandoms(searchFandomParam.getCurrentSn(), searchFandomParam.getSearchVal(), pageBounds);
    }

    public FandomInfoResult addFandom(AddFandomParam addFandomParam) {
        int count = this.fandomMapper.getCountByFandomName(addFandomParam.getFandomName());
        if (count > 0) {
            throw new ApplicationException(Constants.FANDOM_NAME_REPATEDD, "Fandom name repeated");
        }
        FandomEntity fandomEntity = new FandomEntity();
        fandomEntity.setFandomAdminSn(addFandomParam.getCurrentSn());
        fandomEntity.setFandomAvatarUrl(addFandomParam.getFandomAvatarUrl());
        fandomEntity.setFandomCreateTime(new Date());
        fandomEntity.setFandomCreatorSn(addFandomParam.getCurrentSn());
        fandomEntity.setFandomIntro(addFandomParam.getFandomIntro());
        fandomEntity.setFandomName(addFandomParam.getFandomName());
        fandomEntity.setFandomParentId(addFandomParam.getFandomParentId());
        this.fandomMapper.insert(fandomEntity);

        List<FandomTagResult> fandomTagResultList = saveTagByfandomId(fandomEntity.getId(), addFandomParam.getFandomTagParam());
        FandomInfoResult fandomInfoResult = BeanTools.copyAs(fandomEntity, FandomInfoResult.class);
        fandomInfoResult.setFandomTagResultList(fandomTagResultList);
        return fandomInfoResult;

    }

    @Override
    public int delFandom(DelFandomParam delFandomParam) {
        int count = fandomMapper.delFandom(delFandomParam);
        if (count == 0) {
            throw new ApplicationException(Constants.FANDOM_NO_DELETE, "Need authority to delete");
        }
        return 1;
    }

    @Override
    public FandomInfoResult modifyFandom(ModifyFandomParam modifyFandomParam) {
        if (StringTools.isNotBlank(modifyFandomParam.getFandomName())) {
            FandomInfoResult fandomInfoResult = fandomMapper.getFandomInfo(null, modifyFandomParam.getFandomName());
            if (null != fandomInfoResult && !fandomInfoResult.getId().equals(modifyFandomParam.getId())) {
                throw new ApplicationException(Constants.FANDOM_NAME_REPATEDD, "Fandom name repeated");
            }
        }
        int count2 = fandomMapper.modifyFandom(modifyFandomParam);
        if (count2 == 0) {
            throw new ApplicationException(Constants.FANDOM_MONDIFY_NOT_PERMISSION, "No fandom modify permissions");
        }
        List<FandomTagResult> fandomTagList = saveTagByfandomId(modifyFandomParam.getId(), modifyFandomParam.getFandomTagParam());
        FandomInfoResult fandomInfoResult = fandomMapper.getFandomInfo(modifyFandomParam.getId(), null);
        fandomInfoResult.setFandomTagResultList(fandomTagList);
        return fandomInfoResult;
    }

    /**
     * 添加fandomTag
     *
     * @param fandomId
     * @param fandomTagParamsList
     * @return
     */
    private List<FandomTagResult> saveTagByfandomId(Long fandomId, List<FandomTagParam> fandomTagParamsList) {
        if (null != fandomTagParamsList && null != fandomId) {
            //删除当前fandom的所有tag信息
            fandomTagMapper.deleteFandomTagByFandomId(fandomId);
            //重新添加当前fandom的tag信息
            for (int i = 0; i < fandomTagParamsList.size(); i++) {
                FandomTagParam fandomTagParam = fandomTagParamsList.get(i);
                fandomTagParam.setFandomId(fandomId);
                fandomTagMapper.saveTagByfandomId(fandomTagParam);
            }
            return fandomTagMapper.selectFandomTagsByFandomId(fandomId);
        }
        return null;
    }

}
