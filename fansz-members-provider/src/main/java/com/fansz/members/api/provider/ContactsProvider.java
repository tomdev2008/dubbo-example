package com.fansz.members.api.provider;

import com.fansz.members.api.ContactsApi;
import com.fansz.members.api.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

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
     * 获取所有候选人接口
     * @return resp 返回对象
     */
    public Response getFollowRequest()
    {
        try {

             contactsService.getCandidates(null);
        } catch (Exception iae) {

        }
        return null;
    }
}
