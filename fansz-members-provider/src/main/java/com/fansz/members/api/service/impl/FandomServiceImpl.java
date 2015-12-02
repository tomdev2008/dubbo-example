package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.post.GetPostsParam;
import com.fansz.members.tools.BeanTools;
import org.springframework.stereotype.Service;

import com.fansz.members.api.entity.FandomMemberEntity;
import com.fansz.members.api.repository.FandomMemberEntityMapper;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.repository.FandomPostEntityMapper;
import com.fansz.members.api.repository.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
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
}
