package com.fansz.members.api.service.impl;

import com.fansz.members.api.repository.SpecialFocusMapper;
import com.fansz.members.api.service.SpecialFocusService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusResult;
import com.fansz.members.tools.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/12/9.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SpecialFocusImpl implements SpecialFocusService{
    @Autowired
    private SpecialFocusMapper specialFocusMapper;

    @Override
    public List<SpecialFocusResult> getSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        return specialFocusMapper.getSpecialFocusInfo(specialFocusParam);
    }

    @Override
    public int addSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        int count = specialFocusMapper.getCount(specialFocusParam);
        if(count > 0){
            throw new ApplicationException(Constants.RELATION_IS_IN_FANDOM, "already special focus");
        }
        return specialFocusMapper.addSpecialFocusInfo(specialFocusParam);
    }

    @Override
    public int modifySpecialFocusInfo(ModifySpecialFocusParam modifySpecialFocusParam) {
        Map map = null;
        String memberSn = modifySpecialFocusParam.getMemberSn();
        List<SpecialFocusParam> SpecialFocusParamList = modifySpecialFocusParam.getList();
        for (SpecialFocusParam specialFocusParam:SpecialFocusParamList) {
            map = new HashMap();
            map.put("postingTag",specialFocusParam.getPostingTag());
            map.put("memberSn",memberSn);
            map.put("specialMemberSn",specialFocusParam.getSpecialMemberSn());
            map.put("specialFandomId",specialFocusParam.getSpecialFandomId());
            specialFocusMapper.modifySpecialFocusInfo(map);
        }
        return SpecialFocusParamList.size();
    }

    @Override
    public int delSpecialFocusInfo(SpecialFocusParam specialFocusParam) {
        return specialFocusMapper.delSpecialFocusInfo(specialFocusParam);
    }
}
