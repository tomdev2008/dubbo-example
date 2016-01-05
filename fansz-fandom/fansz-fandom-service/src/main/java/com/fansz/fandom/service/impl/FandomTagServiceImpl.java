package com.fansz.fandom.service.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.fandom.model.fandom.FandomTagParam;
import com.fansz.fandom.model.fandom.FandomTagResult;
import com.fansz.fandom.repository.FandomTagMapper;
import com.fansz.fandom.service.FandomTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dell on 2015/12/29.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FandomTagServiceImpl implements FandomTagService{

    @Autowired
    private FandomTagMapper fandomTagMapper;

    @Override
    public List<FandomTagResult> selectFandomTagsByFandomId(FandomTagParam fandomTagParam) {
        List<FandomTagResult> tagFandomRelationList = fandomTagMapper.selectFandomTagsByFandomId(fandomTagParam.getFandomId());
        return tagFandomRelationList;
    }

    @Override
    public FandomTagResult deleteFandomTagByTagId(FandomTagParam fandomTagParam) {
        FandomTagResult fandomTagResult = fandomTagMapper.getFandomTagById(fandomTagParam.getId());
        if(null == fandomTagResult){
            return null;
        }
        fandomTagMapper.deleteFandomTagByTagId(fandomTagResult.getId());
        return fandomTagResult;
    }

    @Override
    public String saveTagByfandomId(FandomTagParam fandomTagParam) {
        int count =  fandomTagMapper.getTagCountByName(fandomTagParam.getTagName(),fandomTagParam.getFandomId());
        if(count > 0){ //同一个fandom下不允许有重名的tag
            return ErrorCode.TAG_NAME_NOT_UNIQUE.getCode();
        }
        fandomTagMapper.saveTagByfandomId(fandomTagParam);
        return null;
    }
}
