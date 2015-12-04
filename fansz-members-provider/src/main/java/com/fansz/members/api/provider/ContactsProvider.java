package com.fansz.members.api.provider;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.api.ContactsApi;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.service.ContactsService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.post.PostLikeInfoResult;
import com.fansz.members.model.profile.ContactQueryParam;
import com.fansz.members.model.relationship.FriendsQueryParam;
import com.fansz.members.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 好友关系接口类
 * Created by root on 15-11-3.
 */
@Component("contactsProvider")
public class ContactsProvider implements ContactsApi{

    @Autowired
    private ContactsService contactsService;

    /**
     * 添加好友接口
     * @param followId 好友用户
     * @return resp 返回对象
     */
    @POST
    @Path("/friend/add/{followId}")
    @Produces("application/json")
    public Response addFriend(@PathParam("followId") String followId)
    {
        try {
            contactsService.addFriend(followId, followId);

        } catch (Exception iae) {
        }
        return null;
    }

    /**
     * 接受添加接口
     * @param followId 好友用户id
     * @return resp 返回对象
     */
    public Response acceptFriend(String followId)
    {
        try {
             contactsService.acceptFriend(followId, followId);

        } catch (Exception iae) {
        }
        return null;
    }

    /**
     * 删除好友接口
     * @param id 好友用户id
     * @return resp 返回对象
     */
    public Response removeFriend(@PathParam("id") String id) {
            contactsService.removeFriend(id, id);

        return null;
    }

    /**
     * 获取我所有好友接口
     * @return resp 返回对象
     */
    public Response getFriends()
    {
        try {
            contactsService.getFriends(null);
        } catch (Exception iae) {

        }
        return null;
    }

    /**
     * 获取好友详细信息接口
     * @param id 好友用户id
     * @return resp 返回对象
     */
    public Response getFriendProfile(@PathParam("id") String id)
    {
        try {
             contactsService.getFriend(null, id);
        } catch (Exception iae) {
        }
        return null;
    }

    /**
     * 搜索好友接口
     * @param criteria 查询条件
     * @return resp 返回对象
     */
    public Response findFriend(String criteria)
    {
        try {

            contactsService.findFriend(null);
        } catch (IllegalArgumentException iae) {
        }
        return null;
    }

    /**
     * 获取用户详细信息接口
     * @param id 用户id
     * @return resp 返回对象
     */
    public Response getUserProfile(String id)
    {
        try {
            contactsService.getFriend(null, id);

        } catch (Exception iae) {

        }
        return null;
    }

    /**
     * 上传用户通讯录，搜索出通讯录好友（包含好友状态）
     */
    @POST
    @Path("/album")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<String>> getMembersAlbum(ContactQueryParam contractQueryParam) {
        CommonResult<List<String>> commonResult = new CommonResult<>();

        try {
            if (contractQueryParam != null)
            {
                List<String> images = contactsService.getImages(contractQueryParam);
                commonResult.setResult(images);
                commonResult.setStatus(Constants.SUCCESS);
            } else {
                commonResult.setStatus(Constants.FAIL);
                commonResult.setMessage("json is null");
            }

        } catch (Exception e) {
            throw new ApplicationException("Get special friend fail", e.getMessage());
        }
        return commonResult;
    }

}
