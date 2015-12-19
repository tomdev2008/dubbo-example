package com.fansz.members.consumer.rpc;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fansz.members.api.*;
import com.fansz.members.consumer.rpc.RpcInvoker;
import com.fansz.members.consumer.utils.JsonHelper;
import com.fansz.members.consumer.utils.JsonHelper;
import com.fansz.members.model.account.*;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentFromFandomQueryParam;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.message.QueryMessageParam;
import com.fansz.members.model.post.*;
import com.fansz.members.model.profile.*;
import com.fansz.members.model.relationship.*;
import com.fansz.members.model.search.SearchMemberParam;
import com.fansz.members.model.search.SearchParam;
import com.fansz.members.model.seedingspot.SeedingSpotPrama;
import com.fansz.members.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
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

    private Logger logger = LoggerFactory.getLogger(DubboInvoker.class);

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

    @Resource(name = "specialFocusProvider")
    private SpecialFocusApi specialFocusApi;

    @Resource(name = "messageCenterProvider")
    private MessageCenterApi messageCenterApi;

    public DubboInvoker() {

    }

    public String invoke(String url, String requestBody) {
        Map<String, Object> reqMap = parseParameter(requestBody);
        String header = JsonHelper.toString(reqMap.get("header"));
        List reqArray = (List) reqMap.get("request");

        List<String> responseList = mergeRequest(reqArray);
        String response = StringUtils.join(responseList, ",");
        return String.format("{\"header\":%s,\"response\": [%s]}", header, response);
    }

    private Map<String, Object> parseParameter(String requestBody) {
        Map<String, Object> reqMap = JsonHelper.parseObject(requestBody);
        return reqMap;
    }

    /**
     * 合并多个请求
     *
     * @param reqArray
     * @return
     */
    private List<String> mergeRequest(List<Map<String, Object>> reqArray) {
        List<String> responseList = new ArrayList<>();
        for (int i = 0; i < reqArray.size(); i++) {
            Map<String, Object> req = reqArray.get(i);
            String method = (String) req.get("method");
            Map<String, Object> params = (Map<String, Object>) req.get("params");
            String response = "";
            try {
                response = invokeRpc(method, params);
            } catch (Exception e) {
                logger.error("调用RPC服务出错!", e);
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
            VerifyCodeParam verifyCodeParam = JsonHelper.copyAs(params, VerifyCodeParam.class);
            result = verifyCodeApi.getVerifyCodeForRegister(verifyCodeParam);
        } else if ("getVerifyCodeForReset".equals(method)) {//2
            VerifyCodeParam verifyCodeParam = JsonHelper.copyAs(params, VerifyCodeParam.class);
            result = verifyCodeApi.getVerifyCodeForReset(verifyCodeParam);
        } else if ("register".equals(method)) {//3
            RegisterParam registerParam = JsonHelper.copyAs(params, RegisterParam.class);
            result = accountApi.register(registerParam);
        } else if ("login".equals(method)) {//4
            LoginParam loginParam = JsonHelper.copyAs(params, LoginParam.class);
            result = accountApi.login(loginParam);
        } else if ("resetPassword".equals(method)) {//5
            ResetPasswordParam resetPasswordParam = JsonHelper.copyAs(params, ResetPasswordParam.class);
            result = accountApi.resetPassword(resetPasswordParam);
        } else if ("modifyPassword".equals(method)) {//6
            ChangePasswordParam changePasswordParam = JsonHelper.copyAs(params, ChangePasswordParam.class);
            result = accountApi.changePassword(changePasswordParam);
        } else if ("logout".equals(method)) {//7
            LogoutParam logoutParam = JsonHelper.copyAs(params, LogoutParam.class);
            result = accountApi.logout(logoutParam);
        } else if ("modifyMyProfile".equals(method)) {//8
            ModifyProfileParam modifyProfileParam = JsonHelper.copyAs(params, ModifyProfileParam.class);
            result = profileApi.modifyProfile(modifyProfileParam);
        } else if ("getProfile".equals(method)) {//9
            QueryProfileParam queryProfileParam = JsonHelper.copyAs(params, QueryProfileParam.class);
            result = profileApi.getProfile(queryProfileParam);
        } else if ("getFriends".equals(method)) {//10
            FriendsQueryParam friendsQueryParam = JsonHelper.copyAs(params, FriendsQueryParam.class);
            result = relationShipApi.getFriends(friendsQueryParam);
        } else if ("searchContacts".equals(method)) {//11
            ContactQueryParam contactQueryParam = JsonHelper.copyAs(params, ContactQueryParam.class);
            result = profileApi.getContactInfo(contactQueryParam);
        } else if ("requestToBeFriends".equals(method)) {//12
            AddFriendParam addFriendParam = JsonHelper.copyAs(params, AddFriendParam.class);
            result = relationShipApi.addFriendRequest(addFriendParam);
        } else if ("agreeAddRequest".equals(method)) {//13
            OpRequestParam opRequestParam = JsonHelper.copyAs(params, OpRequestParam.class);
            result = relationShipApi.agreeRequest(opRequestParam);
        } else if ("beMySpecialFriend".equals(method)) {//14
            AddFriendParam addFriendParam = JsonHelper.copyAs(params, AddFriendParam.class);
            result = relationShipApi.addSpecialFriend(addFriendParam);
        } else if ("removeMySpecialFriend".equals(method)) {//15
            AddFriendParam addFriendParam = JsonHelper.copyAs(params, AddFriendParam.class);
            result = relationShipApi.cancelSpecialFriend(addFriendParam);
        } else if ("searchMembers".equals(method)) {//16
            SearchParam searchParam = JsonHelper.copyAs(params, SearchParam.class);
            result = profileApi.searchMembersByKey(searchParam);
        } else if ("setMemberType".equals(method)) {//17
            SetMemberParam setMemberParam = JsonHelper.copyAs(params, SetMemberParam.class);
            result = profileApi.setMemberType(setMemberParam);
        } else if ("getFriendRequests".equals(method)) {//18
            FriendsQueryParam friendsQueryParam = JsonHelper.copyAs(params, FriendsQueryParam.class);
            result = relationShipApi.getFriendRquests(friendsQueryParam);
        } else if ("getRequesters".equals(method)) {//19
            FriendsQueryParam friendsQueryParam = JsonHelper.copyAs(params, FriendsQueryParam.class);
            result = relationShipApi.getRequesters(friendsQueryParam);
        } else if ("getSpecialFriend".equals(method)) {//20
            FriendsQueryParam friendsQueryParam = JsonHelper.copyAs(params, FriendsQueryParam.class);
            result = relationShipApi.getSpecialFriends(friendsQueryParam);
        } else if ("getFamousers".equals(method)) {//21
            SearchMemberParam searchMemberParam = JsonHelper.copyAs(params, SearchMemberParam.class);
            result = profileApi.searchMembersByType(searchMemberParam);
        }//connects ends
        else if ("createFandom".equals(method)) {//1
            AddFandomParam addFandomParam = JsonHelper.copyAs(params, AddFandomParam.class);
            result = fandomApi.addFandom(addFandomParam);
        } else if ("joinFandom".equals(method)) {//2
            JoinFandomParam joinFandomParam = JsonHelper.copyAs(params, JoinFandomParam.class);
            result = fandomApi.joinFandom(joinFandomParam);
        } else if ("exitFandom".equals(method)) {//3
            ExitFandomParam exitFandomParam = JsonHelper.copyAs(params, ExitFandomParam.class);
            result = fandomApi.exitFandom(exitFandomParam);
        } /**else if ("beMySpecialFandom".equals(method)) {//4
            JoinFandomParam joinFandomParam = JsonHelper.copyAs(params, JoinFandomParam.class);
            result = fandomApi.markSpecialFandom(joinFandomParam);
        } else if ("removeMySpecialFandom".equals(method)) {//5
            JoinFandomParam joinFandomParam = JsonHelper.copyAs(params, JoinFandomParam.class);
            result = fandomApi.removeSpecialFandom(joinFandomParam);
        } **/else if ("getRecommendFandom".equals(method)) {//6
            FandomQueryParam fandomQueryParam = JsonHelper.copyAs(params, FandomQueryParam.class);
            result = fandomApi.getRecommendFandom(fandomQueryParam);
        } else if ("getMyfandoms".equals(method)) {//7
            MemberFandomQueryParam memberFandomQueryParam = JsonHelper.copyAs(params, MemberFandomQueryParam.class);
            result = fandomApi.getMyFandoms(memberFandomQueryParam);
        } else if ("getFandomInfo".equals(method)) {//8
            FandomInfoParam fandomInfoParam = JsonHelper.copyAs(params, FandomInfoParam.class);
            result = fandomApi.getFandom(fandomInfoParam);
        } else if ("searchFandoms".equals(method)) {//9
            SearchFandomParam searchFandomParam = JsonHelper.copyAs(params, SearchFandomParam.class);
            result = fandomApi.searchFandoms(searchFandomParam);
        } else if ("listAllFandoms".equals(method)) {//10
            FandomQueryParam fandomQueryParam = JsonHelper.copyAs(params, FandomQueryParam.class);
            result = fandomApi.listAllFandoms(fandomQueryParam);
        } else if ("getFandomCategory".equals(method)) {//11
            FandomQueryParam fandomQueryParam = JsonHelper.copyAs(params, FandomQueryParam.class);
            result = fandomApi.getFandomCategory(fandomQueryParam);
        } else if ("getFandomMembers".equals(method)) {//12
            FandomQueryParam fandomQueryParam = JsonHelper.copyAs(params, FandomQueryParam.class);
            result = fandomApi.getFandomMembers(fandomQueryParam);
        } else if ("listMyFandomsPost".equals(method)) {//13
            GetPostsParam getPostsParam = JsonHelper.copyAs(params, GetPostsParam.class);
            result = postApi.listMyFandomPosts(getPostsParam);
        } else if ("listMyNewsfeeds".equals(method)) {//14
            GetPostsParam getPostsParam = JsonHelper.copyAs(params, GetPostsParam.class);
            result = postApi.listFriendsPosts(getPostsParam);
        } else if ("listPostInFandom".equals(method)) {//15
            GetMemberFandomPostsParam getMemberFandomPostsParam = JsonHelper.copyAs(params, GetMemberFandomPostsParam.class);
            result = postApi.getMemberPostsByFandom(getMemberFandomPostsParam);
        } else if ("publishPost".equals(method)) {//16
            AddPostParam addPostParam = JsonHelper.copyAs(params, AddPostParam.class);
            result = postApi.addPost(addPostParam);
        } else if ("commentPost".equals(method)) {//17
            CommentParam commentParam = JsonHelper.copyAs(params, CommentParam.class);
            result = commentApi.addPostComment(commentParam);
        } else if ("listPostComments".equals(method)) {//18
            CommentFromFandomQueryParam commentFromFandomQueryParam = JsonHelper.copyAs(params, CommentFromFandomQueryParam.class);
            result = commentApi.getCommentsByPostidFromFandom(commentFromFandomQueryParam);
        } else if ("deletePost".equals(method)) {//19
            RemovePostParam removePostParam = JsonHelper.copyAs(params, RemovePostParam.class);
            result = postApi.removePost(removePostParam);
        } else if ("listPostVoters".equals(method)) {//20
            PostParam postParam = JsonHelper.copyAs(params, PostParam.class);
            result = postApi.listPostVoteList(postParam);
        } else if ("votePost".equals(method)) {//21
            AddLikeParam addLikeParam = JsonHelper.copyAs(params, AddLikeParam.class);
            result = postApi.addLike(addLikeParam);
        } else if ("deleteVote".equals(method)) {//22
            DeleteLikeParam deleteLikeParam = JsonHelper.copyAs(params, DeleteLikeParam.class);
            result = postApi.deleteLike(deleteLikeParam);
        } else if ("listFandomPosts".equals(method)) {//23
            PostsQueryParam postsQueryParam = JsonHelper.copyAs(params, PostsQueryParam.class);
            result = postApi.getPostsByFandom(postsQueryParam);
        } else if ("searchPosts".equals(method)) {//24
            SearchPostParam searchPostParam = JsonHelper.copyAs(params, SearchPostParam.class);
            result = postApi.searchPosts(searchPostParam);
        } else if ("getPost".equals(method)) {//25
            GetPostByIdParam postParam = JsonHelper.copyAs(params, GetPostByIdParam.class);
            result = postApi.getPost(postParam);
        } else if ("replyComment".equals(method)) {//26
            CommentParam commentParam = JsonHelper.copyAs(params, CommentParam.class);
            result = commentApi.replyComment(commentParam);
        } else if ("deleteComment".equals(method)) {//27
            CommentDelParam commentDelParam = JsonHelper.copyAs(params, CommentDelParam.class);
            result = commentApi.removeCommet(commentDelParam);
        } else if ("getMembersAlbum".equals(method)) {//28
            ContactQueryParam contactQueryParam = JsonHelper.copyAs(params, ContactQueryParam.class);
            result = profileApi.getMembersAlbum(contactQueryParam);
        } else if ("getRecommendInfo".equals(method)) {//28
            SeedingSpotPrama seedingSpotPrama = JsonHelper.copyAs(params, SeedingSpotPrama.class);
            result = seedingSpotApi.getSeedingSpot(seedingSpotPrama);
        } else if ("getMemberAllPosts".equals(method)) {
            GetMemberPostsParam postParam = JsonHelper.copyAs(params, GetMemberPostsParam.class);
            result = postApi.getAllPostsByMember(postParam);
        } else if ("validateNickname".equals(method)) {
            NicknameCheckParam nicknameCheckParam = JsonHelper.copyAs(params, NicknameCheckParam.class);
            result = profileApi.validateNickName(nicknameCheckParam);
        } else if ("getAllSpecialFocus".equals(method)) {
            SpecialFocusParam specialFocusParam = JsonHelper.copyAs(params, SpecialFocusParam.class);
            result = specialFocusApi.getSpecialFocusInfo(specialFocusParam);
        } else if ("setSpecialFocusOrder".equals(method)) {
            ModifySpecialFocusParam modifySpecialFocusParam = JsonHelper.copyAs(params, ModifySpecialFocusParam.class);
            result = specialFocusApi.modifySpecialFocusInfo(modifySpecialFocusParam);
        } else if ("myRemindCenter".equals(method)) {
            QueryMessageParam queryMessageParam  = JsonHelper.copyAs(params, QueryMessageParam.class);
            result = messageCenterApi.getMessageByMemberSn(queryMessageParam);
        }
        else if ("addJoinFandom".equals(method)) {
            AddFandomParam addFandomParam  = JsonHelper.copyAs(params, AddFandomParam.class);
            result = fandomApi.addJoinFandom(addFandomParam);
        }else if("delFandom".equals(method)){
            DelFandomParam delFandomParam = JsonHelper.copyAs(params,DelFandomParam.class);
            result = fandomApi.delFandom(delFandomParam);
        }else if("modifyFandom".equals(method)){
            ModifyFandomParam modifyFandomParam = JsonHelper.copyAs(params,ModifyFandomParam.class);
            result = fandomApi.modifyFandom(modifyFandomParam);
        }
        return result == null ? "{\"message\": \"method name error\", \"result\": {}, \"status\": \"10001\"}" : JsonHelper.toString(result);
    }
}
