package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.MemberPostEntity;
import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.model.post.PostParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface FandomPostEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FandomPostEntity record);

    int insertSelective(FandomPostEntity record);

    FandomPostEntity selectByPrimaryKey(Long id);

    List<FandomPostEntity> selectNewByFandomId(Long id);

    List<FandomPostEntity> selectHotByFandomId(Long id);

    int updateByPrimaryKeySelective(FandomPostEntity record);

    int updateByPrimaryKey(FandomPostEntity record);

    PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn, PageBounds pageBounds);

    PageList<PostInfoResult> findPostsOfMyFriends(String memberSn, PageBounds pageBounds);

    PageList<PostInfoResult> searchPosts(String searchVal, PageBounds pageBounds);

    PostInfoResult getPost(PostParam postParam);

    /**
     * 查询某人在某个fandom的所有帖子列表
     *
     * @param fandomId
     * @param memberSn
     * @return
     */
    List<MemberPostEntity> memberFandomPosts(@Param("fandomId") long fandomId, @Param("memberSn") String memberSn);

    /**
     * 查询某人在某个fandom的所有帖子列表总数
     *
     * @param fandomId
     * @param memberSn
     * @return
     */
    int countMemberFandomPosts(@Param("fandomId") long fandomId, @Param("memberSn") String memberSn);

}