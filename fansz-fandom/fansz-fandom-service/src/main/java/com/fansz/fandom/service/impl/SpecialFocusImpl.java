package com.fansz.fandom.service.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.fandom.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusResult;
import com.fansz.fandom.repository.SpecialFocusMapper;
import com.fansz.fandom.service.SpecialFocusService;
import com.fansz.fandom.tools.Constants;
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
public class SpecialFocusImpl implements SpecialFocusService {
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
            throw new ApplicationException(ErrorCode.RELATION_IS_IN_FANDOM);
        }
        return specialFocusMapper.addSpecialFocusInfo(specialFocusParam);
    }

    @Override
    public int modifySpecialFocusInfo(ModifySpecialFocusParam modifySpecialFocusParam) {
        Map map = null;
        String memberSn = modifySpecialFocusParam.getCurrentSn();
        List<SpecialFocusParam> SpecialFocusParamList = modifySpecialFocusParam.getList();
        for (SpecialFocusParam specialFocusParam:SpecialFocusParamList) {
            map = new HashMap();
            map.put("postingTag",specialFocusParam.getPostingTag());
            map.put("currentSn",memberSn);
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
