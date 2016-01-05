package com.fansz.fandom.repository;

import com.fansz.fandom.model.fandom.FandomTagParam;
import com.fansz.fandom.model.fandom.FandomTagResult;
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

    int getTagCountByName(String tagName,Long fandomId);

    FandomTagResult getFandomTagById(Long tagId);

}
