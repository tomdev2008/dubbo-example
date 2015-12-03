package com.fansz.members.api.service;

import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.fandom.*;

import com.fansz.members.model.post.GetPostsParam;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {

    List<FandomInfoResult> listFandom(FandomQueryParam fandomQueryParam);

    List<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam);
}
