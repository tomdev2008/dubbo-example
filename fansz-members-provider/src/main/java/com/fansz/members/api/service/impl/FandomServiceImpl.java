package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.model.fandom.FandomCategorys;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.fandom.FandomQueryParam;
import com.fansz.members.model.profile.ContactInfoResult;
import com.fansz.members.tools.BeanTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
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
    public List<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam) {
        PageBounds pageBounds = new PageBounds(fandomQueryParam.getPageNum(), fandomQueryParam.getCount());
        return fandomMapper.getRecommendFandom(pageBounds);
    }

    @Override
    public List<FandomCategorys> getFandomCategory(FandomQueryParam fandomQueryParam) {
        List<FandomCategorys> result = new ArrayList<>();
        List<FandomInfoResult> rootCategory =  fandomMapper.getFandomCategory(Long.parseLong("0"));

        List<FandomInfoResult> childCategory = null;
        FandomCategorys fandomCategorys = null;
        if(rootCategory != null)
        {
            for (FandomInfoResult root : rootCategory)
            {
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
    public List<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam) {
//        PageBounds pageBounds = new PageBounds(fandomQueryParam.getPageNum(), fandomQueryParam.getCount());
//        return fandomMapper.getFandomMembers(fandomQueryParam, pageBounds);
        return fandomMapper.getFandomMembers(fandomQueryParam);
    }
}
