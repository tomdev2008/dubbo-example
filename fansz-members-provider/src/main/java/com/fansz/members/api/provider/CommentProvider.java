package com.fansz.members.api.provider;

import com.fansz.members.api.CommentApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 评论接口类
 * Created by root on 15-11-4.
 */
@Component("commentProvider")
public class CommentProvider extends AbstractProvider implements CommentApi {

    @Autowired
    private CommentService commentService;


    /**
     * 发布帖子评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    public CommonResult<NullResult> addPostComment(CommentParam commentPara) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setResult(null);
        try {
            if (null != commentPara) {
                commentService.addComment(commentPara);
                result.setStatus(Constants.SUCCESS);
            } else {
                result.setStatus(Constants.FAIL);
                result.setMessage("json is null");
            }
        } catch (Exception e) {
            result.setStatus(Constants.FAIL);
        }
        return result;
    }

    /**
     * 回复评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    public CommonResult<NullResult> replyComment(CommentParam commentPara) {
        return this.addPostComment(commentPara);
    }

    /**
     * 删除评论接口
     *
     * @param commentDelParam 评论id
     * @return resp 返回对象
     */
    public CommonResult<NullResult> removeCommet(CommentDelParam commentDelParam) {
        commentService.removeComment(commentDelParam);
        return renderSuccess();
    }
}
