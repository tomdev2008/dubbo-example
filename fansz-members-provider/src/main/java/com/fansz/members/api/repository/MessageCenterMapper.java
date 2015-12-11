package com.fansz.members.api.repository;

import com.fansz.members.model.messagecenter.MessageCenterResult;
import com.fansz.members.model.search.SearchParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Created by dell on 2015/12/11.
 */
@MapperScan
public interface MessageCenterMapper {

    PageList<MessageCenterResult> getMessageByMemberSn(@Param("memberSn") String memberSn, PageBounds pageBounds);

}
