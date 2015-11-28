package com.fansz.members.api.provider;

import com.fansz.members.api.CommentApi;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentPagedParam;
import com.fansz.members.model.comment.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

/**
 * 评论接口类
 * Created by root on 15-11-4.
 */
@Component("commentProvider")
public class CommentProvider implements CommentApi{

    @Autowired
    private CommentService commentService;


    /**
     * 发布评论接口
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    public Response addComment(CommentParam commentPara)
    {
        return null;
    }

    /**
     * 删除评论接口
     * @param commentDelParam 评论id
     * @return resp 返回对象
     */
    public Response removeCommet(CommentDelParam commentDelParam)
    {
        return null;
    }

    /**
     * 获取评论详细信息接口
     * @param commentPagePara 评论参数
     * @return resp 返回对象
     */
    public Response getComments(CommentPagedParam commentPagePara)
    {
       return null;
    }
}
