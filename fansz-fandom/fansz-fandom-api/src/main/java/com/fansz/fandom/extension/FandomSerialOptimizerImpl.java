package com.fansz.fandom.extension;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.fandom.model.comment.PostCommentQueryResult;
import com.fansz.fandom.model.fandom.FandomCategory;
import com.fansz.fandom.model.fandom.FandomInfoResult;
import com.fansz.fandom.model.messagecenter.MessageCenterResult;
import com.fansz.fandom.model.post.GetPostInfoResult;
import com.fansz.fandom.model.post.PostInfoResult;
import com.fansz.fandom.model.post.PostLikeInfoResult;
import com.fansz.fandom.model.post.SearchPostResult;
import com.fansz.fandom.model.profile.ProfileValidateResult;
import com.fansz.fandom.model.profile.UserInfoResult;
import com.fansz.fandom.model.seedingspot.SeedingSpotResult;
import com.fansz.fandom.model.specialfocus.SpecialFocusResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by allan on 15/12/28.
 */
public class FandomSerialOptimizerImpl implements SerializationOptimizer {
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<>();
        classes.add(PageList.class);
        classes.add(CommonResult.class);
        classes.add(PostCommentQueryResult.class);
        classes.add(FandomInfoResult.class);
        classes.add(FandomCategory.class);

        classes.add(MessageCenterResult.class);

        classes.add(GetPostInfoResult.class);
        classes.add(PostInfoResult.class);
        classes.add(PostLikeInfoResult.class);
        classes.add(SearchPostResult.class);

        classes.add(ProfileValidateResult.class);
        classes.add(UserInfoResult.class);

        classes.add(SeedingSpotResult.class);
        classes.add(SpecialFocusResult.class);
        return classes;
    }
}