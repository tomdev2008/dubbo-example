package com.fansz.fandom.repository;

import com.fansz.fandom.model.fandom.FandomTagParam;
import com.fansz.fandom.model.fandom.FandomTagResult;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by dell on 2015/12/29.
 */
@MapperScan
public interface FandomTagMapper {

    List<FandomTagResult> selectFandomTagsByFandomId(Long fandomId);

    int deleteFandomTagByTagId(Long tagId);

    int deleteFandomTagByFandomId(Long fandomId);

    int saveTagByfandomId(FandomTagParam fandomTagParam);

    int getTagCountByName(@Param("tagName")String tagName, @Param("fandomId")Long fandomId);

    FandomTagResult getFandomTagById(Long tagId);

}
