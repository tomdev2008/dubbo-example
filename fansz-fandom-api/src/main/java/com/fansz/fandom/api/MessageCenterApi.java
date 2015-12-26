package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.fandom.model.messagecenter.MessageCenterResult;
import com.fansz.fandom.model.messagecenter.QueryMessageParam;

/**
 * Created by dell on 2015/12/11.
 */
@DubboxService("messages")
public interface MessageCenterApi {

    /**
     * 会员获取消息(支持分页)
     *
     * @param queryMessageParam
     * @return
     */
    @DubboxMethod("myRemindCenter")
    CommonPagedResult<MessageCenterResult> getMessageByMemberSn(QueryMessageParam queryMessageParam);

}
