package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.FandomMemberEntity;
import com.fansz.members.api.entity.SingleFandomEntity;
import com.fansz.members.api.event.FandomEventType;
import com.fansz.members.api.event.SpecialRealtionEvent;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.repository.FandomMemberEntityMapper;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.profile.ContactInfoResult;
import com.fansz.members.model.relationship.ExitFandomParam;
import com.fansz.members.model.relationship.JoinFandomParam;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import com.fansz.members.tools.BeanTools;
import com.fansz.members.tools.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ApplicationContext applicationContext;

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
        specialFocusParam.setMemberSn(joinFandomParam.getMemberSn());
        specialFocusParam.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));
        SpecialRealtionEvent specialRealtionEvent = new SpecialRealtionEvent(this, FandomEventType.ADD_SPECIAL, specialFocusParam);
        applicationContext.publishEvent(specialRealtionEvent);

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
        specialFocusParam.setMemberSn(joinFandomParam.getMemberSn());
        specialFocusParam.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));
        SpecialRealtionEvent specialRealtionEvent = new SpecialRealtionEvent(this, FandomEventType.REMOVE_SPECIAL, specialFocusParam);
        applicationContext.publishEvent(specialRealtionEvent);

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
        specialFocusParam.setMemberSn(joinFandomParam.getMemberSn());
        specialFocusParam.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));
        SpecialRealtionEvent specialRealtionEvent = new SpecialRealtionEvent(this, FandomEventType.ADD_SPECIAL, specialFocusParam);
        applicationContext.publishEvent(specialRealtionEvent);

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
        specialFocusParam.setMemberSn(joinFandomParam.getMemberSn());
        specialFocusParam.setSpecialFandomId(Long.parseLong(joinFandomParam.getFandomId()));
        SpecialRealtionEvent specialRealtionEvent = new SpecialRealtionEvent(this, FandomEventType.REMOVE_SPECIAL, specialFocusParam);
        applicationContext.publishEvent(specialRealtionEvent);
        return true;
    }

    public PageList<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam) {
        PageBounds pageBounds = new PageBounds(fandomQueryParam.getOffset(), fandomQueryParam.getLimit());
        return fandomMapper.getRecommendFandom(fandomQueryParam.getMemberSn(), pageBounds);
    }

    @Override
    public List<FandomCategorys> getFandomCategory(FandomQueryParam fandomQueryParam) {
        List<FandomCategorys> result = new ArrayList<>();
        List<FandomInfoResult> rootCategory = fandomMapper.getFandomCategory(Long.parseLong("0"));

        List<FandomInfoResult> childCategory = null;
        FandomCategorys fandomCategorys = null;
        if (rootCategory != null) {
            for (FandomInfoResult root : rootCategory) {
                fandomCategorys = new FandomCategorys();

                childCategory = fandomMapper.getFandomCategory(root.getId());

                fandomCategorys.setId(root.getId());
                fandomCategorys.setFandomName(root.getFandomName());
                fandomCategorys.setFandomParentId(root.getFandomParentId());
                fandomCategorys.setFandomAvatarUrl(root.getFandomAvatarUrl());
                fandomCategorys.setFandomIntro(root.getFandomIntro());
                fandomCategorys.setFandomCreateTime(root.getFandomCreateTime());
                fandomCategorys.setChildCategory(childCategory);
                result.add(fandomCategorys);
            }
        }
        return result;
    }

    @Override
    public PageList<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam) {
        PageBounds pageBounds = new PageBounds(fandomQueryParam.getOffset(), fandomQueryParam.getLimit());
        return fandomMemberEntityMapper.getFandomMembers(fandomQueryParam.getFandomId(), fandomQueryParam.getMemberSn(), pageBounds);
    }

    public FandomInfoResult getFandomInfo(FandomInfoParam fandomInfoParam) {
        return fandomMapper.getFandomDetail(fandomInfoParam.getFandomId(), fandomInfoParam.getMemberSn());
    }

    @Override
    public PageList<SearchFandomResult> searchFandoms(SearchFandomParam searchFandomParam) {
        PageBounds pageBounds = new PageBounds(searchFandomParam.getOffset(), searchFandomParam.getLimit());
        return fandomMapper.searchFandoms(searchFandomParam.getMemberSn(), searchFandomParam.getSearchVal(), pageBounds);
    }

    public FandomInfoResult addFandom(AddFandomParam addFandomParam) {
        int count = this.fandomMapper.getCountByFandomName(addFandomParam.getFandomName());
        if (count > 0) {
            throw new ApplicationException(Constants.FANDOM_NAME_REPATEDD, "Fandom name repeated");
        }
        FandomEntity fandomEntity = new FandomEntity();
        fandomEntity.setFandomAdminSn(addFandomParam.getFandomCreatorSn());
        fandomEntity.setFandomAvatarUrl(addFandomParam.getFandomAvatarUrl());
        fandomEntity.setFandomCreateTime(new Date());
        fandomEntity.setFandomCreatorSn(addFandomParam.getFandomCreatorSn());
        fandomEntity.setFandomIntro(addFandomParam.getFandomIntro());
        fandomEntity.setFandomName(addFandomParam.getFandomName());
        fandomEntity.setFandomParentId(addFandomParam.getFandomParentId());
        this.fandomMapper.insert(fandomEntity);
        return BeanTools.copyAs(fandomEntity, FandomInfoResult.class);

    }

    @Override
    public int delFandom(DelFandomParam delFandomParam) {
        return fandomMapper.delFandom(delFandomParam);
    }

    @Override
    public int modifyFandom(ModifyFandomParam modifyFandomParam) {
        return fandomMapper.modifyFandom(modifyFandomParam);
    }
}
