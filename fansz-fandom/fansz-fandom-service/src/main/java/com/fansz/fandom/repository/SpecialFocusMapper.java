package com.fansz.fandom.repository;

import com.fansz.fandom.model.specialfocus.SpecialFocusParam;
import com.fansz.fandom.model.specialfocus.SpecialFocusResult;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
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
    List<SpecialFocusResult> getSpecialFocusInfo(SpecialFocusParam specialFocusParam);

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
