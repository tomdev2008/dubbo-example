package com.fansz.members.api.repository;

import com.fansz.members.model.specialfocus.ModifySpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import com.fansz.members.model.specialfocus.SpecialFocusResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.mybatis.spring.annotation.MapperScan;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Map;

/**
 * Created by dell on 2015/12/9.
 */
@MapperScan
public interface SpecialFocusMapper {

    /**
     *获取某人特别关注的好友和圈子信息
     * @param specialFocusParam
     * @return
     */
    PageList<SpecialFocusResult> getSpecialFocusInfo(SpecialFocusParam specialFocusParam);

    /**
     * 添加特别关注关联信息
     * @param specialFocusParam
     * @return
     */
    int addSpecialFocusInfo(SpecialFocusParam specialFocusParam);

    /**
     * 修改特别关注关联信息
     * @param map
     * @return
     */
    int modifySpecialFocusInfo(Map map);

    /**
     * 删除特别关注关联信息
     * @param specialFocusParam
     * @return
     */
    int delSpecialFocusInfo(SpecialFocusParam specialFocusParam);

    /**
     * 根据条件查询记录条数
     * @param specialFocusParam
     * @return
     */
    int getCount(SpecialFocusParam specialFocusParam);

}
