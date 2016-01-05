package com.fansz.fandom.service;

import com.fansz.fandom.model.fandom.FandomTagParam;
import com.fansz.fandom.model.fandom.FandomTagResult;

import java.util.List;

/**
 * Created by dell on 2015/12/29.
 */
public interface FandomTagService {

    List<FandomTagResult> selectFandomTagsByFandomId(FandomTagParam fandomTagParam);

    FandomTagResult deleteFandomTagByTagId(FandomTagParam fandomTagParam);

    String saveTagByfandomId(FandomTagParam fandomTagParam);

}
