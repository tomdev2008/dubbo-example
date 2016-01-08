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
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.pub.utils.StringTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * fandom服务实现类
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FandomServiceImpl implements FandomService {

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private FandomMemberEntityMapper fandomMemberEntityMapper;

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    @Autowired
    private FandomTagMapper fandomTagMapper;

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
    public List<FandomCategory> getFandomCategory(FandomQueryParam fandomQueryParam) {
        List<FandomCategory> result = new ArrayList<>();
        List<FandomInfoResult> rootCategory = fandomMapper.getFandomCategory(Long.parseLong("0"));

        if (!CollectionTools.isNullOrEmpty(rootCategory)) {
            for (FandomInfoResult root : rootCategory) {
                List<FandomInfoResult> childCategory = fandomMapper.getFandomCategory(root.getId());
                FandomCategory fandomCategory = BeanTools.copyAs(root, FandomCategory.class);
                fandomCategory.setChildCategory(childCategory);
                result.add(fandomCategory);
            }
        }
        return result;
    }

    @Override
    public PageList<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam) {
        PageBounds pageBounds = new PageBounds(fandomQueryParam.getPageNum(), fandomQueryParam.getPageSize());
        return fandomMemberEntityMapper.getFandomMembers(fandomQueryParam.getFandomId(), fandomQueryParam.getCurrentSn(), pageBounds);
    }

    public FandomInfoResult getFandomInfo(FandomInfoParam fandomInfoParam) {
        FandomInfoResult fandomInfoResult = fandomMapper.getFandomDetail(fandomInfoParam.getFandomId(), fandomInfoParam.getCurrentSn());
        List<FandomTagResult> fandomTagList = fandomTagMapper.selectFandomTagsByFandomId(fandomInfoParam.getFandomId());
        fandomInfoResult.setFandomTagResultList(fandomTagList);
        return fandomInfoResult;
    }

    @Override
    public PageList<SearchFandomResult> searchFandoms(SearchFandomParam searchFandomParam) {
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
        return BeanTools.copyAs(fandomEntity, FandomInfoResult.class);

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
        //删除当前fandom的所有tag信息
        fandomTagMapper.deleteFandomTagByFandomId(modifyFandomParam.getId());
        //重新添加当前fandom的tag信息
        for (FandomTagParam fandomTagParam : modifyFandomParam.getFandomTagParam()) {
            fandomTagMapper.saveTagByfandomId(fandomTagParam);
        }
        if (count2 == 0) {
            throw new ApplicationException(Constants.FANDOM_MONDIFY_NOT_PERMISSION, "No fandom modify permissions");
        }
        List<FandomTagResult> fandomTagList = fandomTagMapper.selectFandomTagsByFandomId(modifyFandomParam.getId());
        FandomInfoResult fandomInfoResult = fandomMapper.getFandomInfo(modifyFandomParam.getId(), null);
        fandomInfoResult.setFandomTagResultList(fandomTagList);
        return fandomInfoResult;
    }
}
