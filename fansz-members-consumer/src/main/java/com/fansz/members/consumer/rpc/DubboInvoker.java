package com.fansz.members.consumer.rpc;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fansz.members.api.*;
import com.fansz.members.consumer.rpc.RpcInvoker;
import com.fansz.members.consumer.utils.BeanTools;
import com.fansz.members.model.account.*;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentFromFandomQueryParam;
import com.fansz.members.model.fandom.AddFandomParam;
import com.fansz.members.model.fandom.FandomInfoParam;
import com.fansz.members.model.fandom.FandomQueryParam;
import com.fansz.members.model.fandom.SearchFandomParam;
import com.fansz.members.model.post.*;
import com.fansz.members.model.profile.ContactQueryParam;
import com.fansz.members.model.profile.ModifyProfileParam;
import com.fansz.members.model.profile.QueryProfileParam;
import com.fansz.members.model.relationship.*;
import com.fansz.members.model.search.SearchParam;
import com.fansz.members.model.seedingspot.SeedingSpotPrama;
import com.fansz.members.model.verifycode.VerifyCodeParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by allan on 15/12/5.
 */
@Component("dubboInvoker")
public class DubboInvoker implements RpcInvoker {

    private Logger logger= LoggerFactory.getLogger(DubboInvoker.class);

    @Resource(name = "accountProvider")
    private AccountApi accountApi;

    @Resource(name = "commentProvider")
    private CommentApi commentApi;

    @Resource(name = "fandomProvider")
    private FandomApi fandomApi;

    @Resource(name = "postProvider")
    private PostApi postApi;

    @Resource(name = "profileProvider")
    private ProfileApi profileApi;

    @Resource(name = "relationShipProvider")
    private RelationShipApi relationShipApi;

    @Resource(name = "verifyCodeProvider")
    private VerifyCodeApi verifyCodeApi;

    @Resource(name = "seedingSpotProvider")
    private SeedingSpotApi seedingSpotApi;

    public DubboInvoker() {

    }

    public String invoke(String url, String requestBody) {
        Map<String, Object> reqMap = parseParameter(requestBody);
        String header = JSON.toJSONString(reqMap.get("header"));
        JSONArray reqArray = (JSONArray) reqMap.get("request");

        List<String> responseList = mergeRequest(reqArray);
        String response = StringUtils.join(responseList, ",");
        return String.format("{\"header\":%s,\"response\": [%s]}", header, response);
    }

    private Map<String, Object> parseParameter(String requestBody) {
        Map<String, Object> reqMap = JSON.parseObject(requestBody, Map.class);
        return reqMap;
    }

    /**
     * 合并多个请求
     *
     * @param reqArray
     * @return
     */
    private List<String> mergeRequest(JSONArray reqArray) {
        List<String> responseList = new ArrayList<>();
        for (int i = 0; i < reqArray.size(); i++) {
            JSONObject req = reqArray.getJSONObject(i);
            String method = req.getString("method");
            Map<String, Object> params = (Map<String, Object>) req.get("params");
            String response = "";
            try {
                response = invokeRpc(method, params);
            } catch (Exception e) {
                logger.error("调用RPC服务出错!",e);
                response = "{\"message\": \"System error\", \"result\": {}, \"status\": \"10001\"}";
            }
            String finalString = "{\"method\":\"" + method + "\"," + response.substring(1);
            responseList.add(finalString);
        }
        return responseList;
    }


    /**
     * 调用dubbo服务
     *
     * @param method
     * @param params
     * @return
     */
    private String invokeRpc(String method, Map<String, Object> params) {
        Object result = null;
        if ("getVerifyCodeForRegister".equals(method)) {//1
            VerifyCodeParam verifyCodeParam = BeanTools.copyAs(params, VerifyCodeParam.class);
            result = verifyCodeApi.getVerifyCodeForRegister(verifyCodeParam);

        } else if ("getVerifyCodeForReset".equals(method)) {//2
            VerifyCodeParam verifyCodeParam = BeanTools.copyAs(params, VerifyCodeParam.class);
            result = verifyCodeApi.getVerifyCodeForReset(verifyCodeParam);
        } else if ("register".equals(method)) {//3
            RegisterParam registerParam = BeanTools.copyAs(params, RegisterParam.class);
            result = accountApi.register(registerParam);
        } else if ("login".equals(method)) {//4
            LoginParam loginParam = BeanTools.copyAs(params, LoginParam.class);
            result = accountApi.login(loginParam);
        } else if ("resetPassword".equals(method)) {//5
            ResetPasswordParam resetPasswordParam = BeanTools.copyAs(params, ResetPasswordParam.class);
            result = accountApi.resetPassword(resetPasswordParam);
        } else if ("modifyPassword".equals(method)) {//6
            ChangePasswordParam changePasswordParam = BeanTools.copyAs(params, ChangePasswordParam.class);
            result = accountApi.changePassword(changePasswordParam);
        } else if ("logout".equals(method)) {//7
            LogoutParam logoutParam = BeanTools.copyAs(params, LogoutParam.class);
            result = accountApi.logout(logoutParam);
        } else if ("modifyMyProfile".equals(method)) {//8
            ModifyProfileParam modifyProfileParam = BeanTools.copyAs(params, ModifyProfileParam.class);
            result = profileApi.modifyProfile(modifyProfileParam);
        } else if ("getProfile".equals(method)) {//9
            QueryProfileParam queryProfileParam = BeanTools.copyAs(params, QueryProfileParam.class);
            result = profileApi.getProfile(queryProfileParam);
        } else if ("getFriends".equals(method)) {//10
            FriendsQueryParam friendsQueryParam = BeanTools.copyAs(params, FriendsQueryParam.class);
            result = relationShipApi.getFriends(friendsQueryParam);
        } else if ("searchContacts".equals(method)) {//11
            ContactQueryParam contactQueryParam = BeanTools.copyAs(params, ContactQueryParam.class);
            result = profileApi.getContactInfo(contactQueryParam);
        } else if ("requestToBeFriends".equals(method)) {//12
            AddFriendParam addFriendParam = BeanTools.copyAs(params, AddFriendParam.class);
            result = relationShipApi.addFriendRequest(addFriendParam);
        } else if ("agreeAddRequest".equals(method)) {//13
            OpRequestParam opRequestParam = BeanTools.copyAs(params, OpRequestParam.class);
            result = relationShipApi.agreeRequest(opRequestParam);
        } else if ("beMySpecialFriend".equals(method)) {//14
            AddFriendParam addFriendParam = BeanTools.copyAs(params, AddFriendParam.class);
            result = relationShipApi.addSpecialFriend(addFriendParam);
        } else if ("removeMySpecialFriend".equals(method)) {//15
            AddFriendParam addFriendParam = BeanTools.copyAs(params, AddFriendParam.class);
            result = relationShipApi.cancelSpecialFriend(addFriendParam);
        } else if ("searchMembers".equals(method)) {//16
            SearchParam searchParam = BeanTools.copyAs(params, SearchParam.class);
            result = profileApi.searchMembersByKey(searchParam);
        } else if ("setMemberType".equals(method)) {//17
            ModifyProfileParam modifyProfileParam = BeanTools.copyAs(params, ModifyProfileParam.class);
            result = profileApi.setMemberType(modifyProfileParam);
        } else if ("getFriendRquests".equals(method)) {//18
            FriendsQueryParam friendsQueryParam = BeanTools.copyAs(params, FriendsQueryParam.class);
            result = relationShipApi.getFriendRquests(friendsQueryParam);
        } else if ("getRequesters".equals(method)) {//19
            FriendsQueryParam friendsQueryParam = BeanTools.copyAs(params, FriendsQueryParam.class);
            result = relationShipApi.getRequesters(friendsQueryParam);
        } else if ("getSpecialFriend".equals(method)) {//20
            FriendsQueryParam friendsQueryParam = BeanTools.copyAs(params, FriendsQueryParam.class);
            result = relationShipApi.getSpecialFriends(friendsQueryParam);
        } else if ("getFamousers".equals(method)) {//21
            SearchParam searchParam = BeanTools.copyAs(params, SearchParam.class);
            result = profileApi.searchMembersByType(searchParam);
        }//connects ends
        else if ("createFandom".equals(method)) {//1
            AddFandomParam addFandomParam = BeanTools.copyAs(params, AddFandomParam.class);
            result = fandomApi.addFandom(addFandomParam);
        } else if ("joinFandom".equals(method)) {//2
            JoinFandomParam joinFandomParam = BeanTools.copyAs(params, JoinFandomParam.class);
            result = fandomApi.joinFandom(joinFandomParam);
        } else if ("exitFandom".equals(method)) {//3
            ExitFandomParam exitFandomParam = BeanTools.copyAs(params, ExitFandomParam.class);
            result = fandomApi.exitFandom(exitFandomParam);
        } else if ("beMySpecialFandom".equals(method)) {//4
            JoinFandomParam joinFandomParam = BeanTools.copyAs(params, JoinFandomParam.class);
            result = fandomApi.markSpecialFandom(joinFandomParam);
        } else if ("removeMySpecialFandom".equals(method)) {//5
            JoinFandomParam joinFandomParam = BeanTools.copyAs(params, JoinFandomParam.class);
            result = fandomApi.removeSpecialFandom(joinFandomParam);
        } else if ("getRecommendFandom".equals(method)) {//6
            FandomQueryParam fandomQueryParam = BeanTools.copyAs(params, FandomQueryParam.class);
            result = fandomApi.getRecommendFandom(fandomQueryParam);
        } else if ("getMyfandoms".equals(method)) {//7
            MemberFandomQueryParam memberFandomQueryParam = BeanTools.copyAs(params, MemberFandomQueryParam.class);
            result = fandomApi.getMyFandoms(memberFandomQueryParam);
        } else if ("getFandomInfo".equals(method)) {//8
            FandomInfoParam fandomInfoParam = BeanTools.copyAs(params, FandomInfoParam.class);
            result = fandomApi.getFandom(fandomInfoParam);
        } else if ("searchFandoms".equals(method)) {//9
            SearchFandomParam searchFandomParam = BeanTools.copyAs(params, SearchFandomParam.class);
            result = fandomApi.searchFandoms(searchFandomParam);
        } else if ("listAllFandoms".equals(method)) {//10
            FandomQueryParam fandomQueryParam = BeanTools.copyAs(params, FandomQueryParam.class);
            result = fandomApi.listAllFandoms(fandomQueryParam);
        } else if ("getFandomCategory".equals(method)) {//11
            FandomQueryParam fandomQueryParam = BeanTools.copyAs(params, FandomQueryParam.class);
            result = fandomApi.getFandomCategory(fandomQueryParam);
        } else if ("getFandomMembers".equals(method)) {//12
            FandomQueryParam fandomQueryParam = BeanTools.copyAs(params, FandomQueryParam.class);
            result = fandomApi.getFandomMembers(fandomQueryParam);
        } else if ("listMyFandomsPost".equals(method)) {//13
            GetPostsParam getPostsParam = BeanTools.copyAs(params, GetPostsParam.class);
            result = postApi.listMyFandomPosts(getPostsParam);
        } else if ("listMyNewsfeeds".equals(method)) {//14
            GetPostsParam getPostsParam = BeanTools.copyAs(params, GetPostsParam.class);
            result = postApi.listFriendsPosts(getPostsParam);
        } else if ("listPostInFandom".equals(method)) {//15
            GetMemberFandomPostsParam getMemberFandomPostsParam = BeanTools.copyAs(params, GetMemberFandomPostsParam.class);
            result = postApi.getMemberPostsByFandom(getMemberFandomPostsParam);
        } else if ("publishPost".equals(method)) {//16
            AddPostParam addPostParam = BeanTools.copyAs(params, AddPostParam.class);
            result = postApi.addPost(addPostParam);
        } else if ("commentPost".equals(method)) {//17
            CommentParam commentParam = BeanTools.copyAs(params, CommentParam.class);
            result = commentApi.addPostComment(commentParam);
        } else if ("listPostComments".equals(method)) {//18
            CommentFromFandomQueryParam commentFromFandomQueryParam = BeanTools.copyAs(params, CommentFromFandomQueryParam.class);
            result = commentApi.getCommentsByPostidFromFandom(commentFromFandomQueryParam);
        } else if ("deletePost".equals(method)) {//19
            RemovePostParam removePostParam = BeanTools.copyAs(params, RemovePostParam.class);
            result = postApi.removePost(removePostParam);
        } else if ("listPostVoters".equals(method)) {//20
            PostParam postParam = BeanTools.copyAs(params, PostParam.class);
            result = postApi.listPostVoteList(postParam);
        } else if ("votePost".equals(method)) {//21
            AddLikeParam addLikeParam = BeanTools.copyAs(params, AddLikeParam.class);
            result = postApi.addLike(addLikeParam);
        } else if ("deleteVote".equals(method)) {//22
            DeleteLikeParam deleteLikeParam = BeanTools.copyAs(params, DeleteLikeParam.class);
            result = postApi.deleteLike(deleteLikeParam);
        } else if ("listFandomPosts".equals(method)) {//23
            PostsQueryParam postsQueryParam = BeanTools.copyAs(params, PostsQueryParam.class);
            result = postApi.getPostsByFandom(postsQueryParam);
        } else if ("searchPosts".equals(method)) {//24
            SearchPostParam searchPostParam = BeanTools.copyAs(params, SearchPostParam.class);
            result = postApi.searchPosts(searchPostParam);
        } else if ("getPost".equals(method)) {//25
            PostParam postParam = BeanTools.copyAs(params, PostParam.class);
            result = postApi.getPost(postParam);
        } else if ("replyComment".equals(method)) {//26
            CommentParam commentParam = BeanTools.copyAs(params, CommentParam.class);
            result = commentApi.replyComment(commentParam);
        } else if ("deleteComment".equals(method)) {//27
            CommentDelParam commentDelParam = BeanTools.copyAs(params, CommentDelParam.class);
            result = commentApi.removeCommet(commentDelParam);
        } else if ("getMembersAlbum".equals(method)) {//28
            ContactQueryParam contactQueryParam = BeanTools.copyAs(params, ContactQueryParam.class);
            result = profileApi.getMembersAlbum(contactQueryParam);
        } else if ("getRecommendInfo".equals(method)) {//28
            SeedingSpotPrama seedingSpotPrama = BeanTools.copyAs(params, SeedingSpotPrama.class);
            result = seedingSpotApi.getSeedingSpot(seedingSpotPrama);
        }

        return result == null ? "{\"message\": \"method name error\", \"result\": {}, \"status\": \"10001\"}" : JSON.toJSONString(result);
    }
}
