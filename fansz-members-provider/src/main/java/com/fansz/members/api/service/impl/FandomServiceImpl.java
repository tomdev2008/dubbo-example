package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.FandomMemberEntity;
import com.fansz.members.api.entity.SingleFandomEntity;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.repository.FandomMemberEntityMapper;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.profile.ContactInfoResult;
import com.fansz.members.model.relationship.ExitFandomParam;
import com.fansz.members.model.relationship.JoinFandomParam;
import com.fansz.members.tools.BeanTools;
import com.fansz.members.tools.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * fandom服务实现类
 */
@Service
public class FandomServiceImpl implements FandomService {

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private FandomMemberEntityMapper fandomMemberEntityMapper;

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
    public List<FandomInfoResult> findFandomsByMemberSn(String sn, PageBounds pageBounds) {
        return fandomMemberEntityMapper.findFandomsByMemberSn(sn, pageBounds);
    }

    @Override
    public boolean joinFandom(JoinFandomParam joinFandomParam) {
        FandomMemberEntity fandomMemberEntity = BeanTools.copyAs(joinFandomParam, FandomMemberEntity.class);
        FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(fandomMemberEntity);
        if (exist != null) {
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is already in fandom");
        }
        fandomMemberEntity.setInfatuation("0");//1表示特别关注
        fandomMemberEntityMapper.insert(fandomMemberEntity);
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
        return false;
    }

    @Override
    public boolean markAsSpecial(JoinFandomParam joinFandomParam) {
        FandomMemberEntity queryParam = BeanTools.copyAs(joinFandomParam, FandomMemberEntity.class);
        FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(queryParam);
        if (exist == null) {
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is not in fandom");
        }
        exist.setFandomSn("1");
        fandomMemberEntityMapper.updateByPrimaryKeySelective(exist);
        return true;
    }

    @Override
    public boolean unmarkAsSpecial(JoinFandomParam joinFandomParam) {
        FandomMemberEntity queryParam = BeanTools.copyAs(joinFandomParam, FandomMemberEntity.class);
        FandomMemberEntity exist = fandomMemberEntityMapper.selectByMemberAndFandom(queryParam);
        if (exist == null) {
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "User is not in fandom");
        }
        exist.setFandomSn("0");
        fandomMemberEntityMapper.updateByPrimaryKeySelective(exist);
        return true;
    }

    public List<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam) {
        PageBounds pageBounds = new PageBounds(fandomQueryParam.getOffset(), fandomQueryParam.getLimit());
        return fandomMapper.getRecommendFandom(pageBounds);
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
        PageBounds pageBounds=new PageBounds(fandomQueryParam.getOffset(),fandomQueryParam.getLimit());
        return fandomMapper.getFandomMembers(fandomQueryParam,pageBounds);
    }

    public SingleFandomInfoResult getFandomInfo(FandomInfoParam fandomInfoParam) {

        SingleFandomEntity fandomEntity = this.fandomMapper.findFandomInfo(fandomInfoParam.getFandomId());

        if (fandomEntity == null) {
            return null;
        }

        SingleFandomInfoResult result = new SingleFandomInfoResult();

        result.setFandomName(fandomEntity.getFandomName());
        result.setFandomAvatarUrl(fandomEntity.getFandomAvatarUrl());
        result.setFandomIntro(fandomEntity.getFandomIntro());
        result.setFandomCreateTime(DateFormatUtils.format(fandomEntity.getFandomCreateTime(), "yyyy-MM-dd HH:mm:ss"));

        SingleFandomInfoResult.Creator creator = result.new Creator();
        creator.setSn(fandomEntity.getUserEntity().getSn());
        creator.setNickname(fandomEntity.getUserEntity().getNickname());
        creator.setMemberAvatar(fandomEntity.getUserEntity().getMemberAvatar());

        result.setCreator(creator);

        return result;
    }
}
