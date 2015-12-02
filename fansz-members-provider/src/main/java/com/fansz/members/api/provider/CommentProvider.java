package com.fansz.members.api.provider;

import com.fansz.members.api.CommentApi;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
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
     * 发布帖子评论接口
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    public CommonResult<NullResult> addPostComment(CommentParam commentPara)
    {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setResult(null);
        try{
            if(null != commentPara){
                commentService.addComment(commentPara);
                result.setStatus(Constants.SUCCESS);
            }else{
                result.setStatus(Constants.FAIL);
                result.setMessage("json is null");
            }
        }catch(Exception e){
            result.setStatus(Constants.FAIL);
        }
        return result;
    }

    /**
     * 回复评论接口
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    public CommonResult<NullResult> replyComment(CommentParam commentPara)
    {
       return this.addPostComment(commentPara);
    }

    /**
     * 删除评论接口
     * @param commentDelParam 评论id
     * @return resp 返回对象
     */
    public CommonResult<NullResult> removeCommet(CommentDelParam commentDelParam)
    {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setResult(null);
        try{
            if(null != commentDelParam){
                commentService.removeComment(commentDelParam);
                result.setStatus(Constants.SUCCESS);
            }else{
                result.setStatus(Constants.FAIL);
                result.setMessage("json is null");
            }
        }catch(Exception e){
            result.setStatus(Constants.FAIL);
        }
        return result;
    }

    /**
     * 获取评论详细信息接口
     * @param commentPagePara 评论参数
     * @return resp 返回对象
     */
    public CommonResult<NullResult> getComments(CommentPagedParam commentPagePara)
    {
       return null;
    }
}
