package com.fansz.service.api.service.impl;

import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.StringTools;
import com.fansz.service.api.entity.FandomEntity;
import com.fansz.service.api.entity.FandomMemberEntity;
import com.fansz.service.api.repository.FandomMapper;
import com.fansz.service.api.repository.FandomMemberEntityMapper;
import com.fansz.service.api.service.FandomService;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.model.event.SpecialFocusEvent;
import com.fansz.service.model.event.UnSpecialFocusEvent;
import com.fansz.service.model.fandom.*;
import com.fansz.service.model.profile.ContactInfoResult;
import com.fansz.service.model.relationship.ExitFandomParam;
import com.fansz.service.model.relationship.JoinFandomParam;
import com.fansz.service.model.specialfocus.SpecialFocusParam;
import com.fansz.service.tools.Constants;
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
    public boolean joinFandom(JoinFandomParam joinFandomParam) {
        FandomMemberEntity fandomMemberEntity = BeanTools.copyAs(joinFandomParam, FandomMemberEntity.class);
        FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(fandomMemberEntity);
        if (exist != null) {
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is already in fandom");
        }
        fandomMemberEntity.setInfatuation("1");//1表示特别关注
        fandomMemberEntityMapper.insertSelective(fandomMemberEntity);

        //添加特别关注记录
        SpecialFocusParam specialFocusParam = new SpecialFocusParam();
        specialFocusParam.setCurrentSn(joinFandomParam.getMemberSn());
        specialFocusParam.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));
        eventProducer.produce(AsyncEventType.SPECIAL_FOCUS, new SpecialFocusEvent(specialFocusParam));
        return false;
    }


    @Override
    public boolean exitFandom(ExitFandomParam joinFandomParam) {
        FandomMemberEntity queryParam = BeanTools.copyAs(joinFandomParam, FandomMemberEntity.class);
        FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(queryParam);
        if (exist == null) {
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is not in fandom");
        }
        fandomMemberEntityMapper.deleteByPrimaryKey(exist.getId());

        //删除特别关注记录
        SpecialFocusParam specialFocusParam = new SpecialFocusParam();
        specialFocusParam.setCurrentSn(joinFandomParam.getCurrentSn());
        specialFocusParam.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));

        eventProducer.produce(AsyncEventType.UN_SPECIAL_FOCUS, new UnSpecialFocusEvent(specialFocusParam));
        return false;
    }

    @Override
    public boolean markAsSpecial(JoinFandomParam joinFandomParam) {
        FandomMemberEntity queryParam = BeanTools.copyAs(joinFandomParam, FandomMemberEntity.class);
        FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(queryParam);
        if (exist == null) {
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is not in fandom");
        } else if ("1".equals(exist.getInfatuation())) {
            throw new ApplicationException(Constants.RELATION_IS_SPACIEL_FANDOM, "User has already spaciel followed this fandom");
        }
        exist.setInfatuation("1");
        fandomMemberEntityMapper.updateByPrimaryKeySelective(exist);

        SpecialFocusParam specialFocusParam = new SpecialFocusParam();
        specialFocusParam.setCurrentSn(joinFandomParam.getMemberSn());
        specialFocusParam.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));
        eventProducer.produce(AsyncEventType.SPECIAL_FOCUS, new SpecialFocusEvent(specialFocusParam));

        return true;
    }

    @Override
    public boolean unmarkAsSpecial(JoinFandomParam joinFandomParam) {
        FandomMemberEntity queryParam = BeanTools.copyAs(joinFandomParam, FandomMemberEntity.class);
        FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(queryParam);
        if (exist == null) {
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is not in fandom");
        } else if (!"1".equals(exist.getInfatuation())) {
            throw new ApplicationException(Constants.RELATION_IS_NOT_SPACIEL_FANDOM, "User has not already spaciel followed this fandom");
        }
        exist.setInfatuation("0");
        fandomMemberEntityMapper.updateByPrimaryKeySelective(exist);

        SpecialFocusParam specialFocusParam = new SpecialFocusParam();
        specialFocusParam.setCurrentSn(joinFandomParam.getMemberSn());
        specialFocusParam.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));
        eventProducer.produce(AsyncEventType.UN_SPECIAL_FOCUS, new UnSpecialFocusEvent(specialFocusParam));
        return true;
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
        return fandomMapper.getFandomDetail(fandomInfoParam.getFandomId(), fandomInfoParam.getCurrentSn());
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
        if (count2 == 0) {
            throw new ApplicationException(Constants.FANDOM_MONDIFY_NOT_PERMISSION, "No fandom modify permissions");
        }
        FandomInfoResult fandomInfoResult = fandomMapper.getFandomInfo(modifyFandomParam.getId(), null);
        return fandomInfoResult;
    }
}
