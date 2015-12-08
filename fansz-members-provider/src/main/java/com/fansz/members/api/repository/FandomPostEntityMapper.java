package com.fansz.members.api.repository;

import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.MemberPostEntity;
import com.fansz.members.model.post.GetPostInfoResult;
import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.model.post.PostParam;
import com.fansz.members.model.post.SearchPostResult;
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

    PageList<SearchPostResult> searchPosts(String searchVal, PageBounds pageBounds);

    GetPostInfoResult getPost(PostParam postParam);

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

    /**
     * 查询某人在某个fandom的所有帖子列表
     * @param fandomId
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<MemberPostEntity> newFandomPosts(@Param("fandomId") long fandomId, @Param("pageSize") int pageSize, @Param("pageNum") int pageNum);

    /**
     * 查询某人在某个fandom的所有帖子列表
     * @param fandomId
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<MemberPostEntity> hotFandomPosts(@Param("fandomId") long fandomId, @Param("pageSize") int pageSize, @Param("pageNum") int pageNum);

    /**
     * 查询某人在某个fandom的所有帖子列表总数
     * @param fandomId
     * @return
     */
    int countFandomPosts(@Param("fandomId") long fandomId);

}