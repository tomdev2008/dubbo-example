package com.fansz.fandom.extension;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.comment.AddCommentParam;
import com.fansz.fandom.model.comment.DelCommentParam;
import com.fansz.fandom.model.comment.PostCommentQueryParam;
import com.fansz.fandom.model.comment.PostCommentQueryResult;
import com.fansz.fandom.model.fandom.*;
import com.fansz.fandom.model.messagecenter.MessageCenterResult;
import com.fansz.fandom.model.messagecenter.QueryMessageParam;
import com.fansz.fandom.model.post.*;
import com.fansz.fandom.model.profile.*;
import com.fansz.fandom.model.relationship.ExitFandomParam;
import com.fansz.fandom.model.relationship.FriendsQueryParam;
import com.fansz.fandom.model.relationship.JoinFandomsParam;
import com.fansz.fandom.model.relationship.MemberFandomQueryParam;
import com.fansz.fandom.model.search.SearchMemberParam;
import com.fansz.fandom.model.seedingspot.SeedingSpotResult;
import com.fansz.fandom.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusResult;
import com.fansz.fandom.model.vote.VotePostParam;
import com.fansz.fandom.model.vote.VotePostResult;
import com.fansz.fandom.model.vote.VoteResultByPostId;
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
        classes.add(CommonPagedResult.class);
        classes.add(NullResult.class);

        classes.add(AddCommentParam.class);
        classes.add(DelCommentParam.class);
        classes.add(PostCommentQueryParam.class);
        classes.add(PostCommentQueryResult.class);

        classes.add(AddFandomParam.class);
        classes.add(DelFandomParam.class);
        classes.add(FandomCategory.class);
        classes.add(FandomInfoParam.class);
        classes.add(FandomInfoResult.class);
        classes.add(FandomQueryParam.class);
        classes.add(FandomTagParam.class);
        classes.add(FandomTagResult.class);
        classes.add(ModifyFandomParam.class);
        classes.add(SearchFandomParam.class);

        classes.add(QueryMessageParam.class);
        classes.add(MessageCenterResult.class);

        classes.add(AddLikeParam.class);
        classes.add(AddPostParam.class);
        classes.add(AddVotePostParam.class);
        classes.add(DeleteLikeParam.class);
        classes.add(GetMemberFandomPostsParam.class);
        classes.add(GetMemberPostsParam.class);
        classes.add(GetPostByIdParam.class);
        classes.add(GetPostFandomInfo.class);
        classes.add(GetPostInfoResult.class);
        classes.add(GetPostsParam.class);
        classes.add(PostInfoResult.class);
        classes.add(PostLikeInfoResult.class);
        classes.add(PostParam.class);
        classes.add(PostsQueryParam.class);
        classes.add(RemovePostParam.class);
        classes.add(SearchPostParam.class);
        classes.add(SearchPostResult.class);

        classes.add(ContactQueryParam.class);
        classes.add(ModifyProfileParam.class);
        classes.add(NicknameCheckParam.class);
        classes.add(ProfileValidateResult.class);
        classes.add(QueryProfileParam.class);
        classes.add(SetMemberParam.class);
        classes.add(UserInfoResult.class);

        classes.add(ExitFandomParam.class);
        classes.add(FriendsQueryParam.class);
        classes.add(JoinFandomsParam.class);
        classes.add(MemberFandomQueryParam.class);

        classes.add(SearchMemberParam.class);

        classes.add(SeedingSpotResult.class);
        classes.add(SpecialFocusResult.class);

        classes.add(ModifySpecialFocusParam.class);
        classes.add(SpecialFocusParam.class);
        classes.add(SpecialFocusResult.class);

        classes.add(VotePostParam.class);
        classes.add(VotePostResult.class);
        classes.add(VoteResultByPostId.class);
        return classes;
    }
}