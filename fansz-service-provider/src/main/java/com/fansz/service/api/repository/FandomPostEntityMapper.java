package com.fansz.service.api.repository;

import com.fansz.service.api.entity.FandomPostEntity;
import com.fansz.service.model.post.GetPostByIdParam;
import com.fansz.service.model.post.PostInfoResult;
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

    int updateByPrimaryKeySelective(FandomPostEntity record);

    int updateByPrimaryKey(FandomPostEntity record);

    int incrLikeCountById(Long postId);

    int decrLikeCountById(Long postId);


    int incrCommentCountById(Long postId);

    int decrCommentCountById(Long postId);

    PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn, PageBounds pageBounds);

    PageList<PostInfoResult> findPostsOfMyFriends(String memberSn, PageBounds pageBounds);

    PageList<PostInfoResult> searchPosts(String searchVal, PageBounds pageBounds);

    PostInfoResult getPost(GetPostByIdParam postParam);


    /**
     * 查询某人在某个fandom的所有帖子列表,按照时间倒序排列
     *
     * @param fandomId
     * @return
     */
    PageList<PostInfoResult> listTimedMemberFandomPosts(@Param("fandomId") long fandomId, @Param("memberSn") String memberSn, @Param("mySn") String mySn, PageBounds pageBounds);

    /**
     * 查询某人在某个fandom的所有帖子列表,按照关注人数倒序排列
     *
     * @param fandomId
     * @return
     */
    PageList<PostInfoResult> listHotMemberFandomPosts(@Param("fandomId") long fandomId, @Param("memberSn") String memberSn, @Param("mySn") String mySn, PageBounds pageBounds);


    PageList<PostInfoResult> getPostsAllByMember(@Param("memberSn") String memberSn, @Param("friendSn") String friendSn, PageBounds pageBounds);

}