package com.fansz.fandom.repository;

import com.fansz.fandom.entity.FandomPostEntity;
import com.fansz.fandom.model.post.GetPostByIdParam;
import com.fansz.fandom.model.post.PostInfoResult;
import com.fansz.fandom.model.vote.VotePostParam;
import com.fansz.fandom.model.vote.VotePostResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Map;

@MapperScan
public interface FandomPostEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FandomPostEntity record);

    FandomPostEntity selectByPrimaryKey(Long id);

    int incrLikeCountById(Long postId);

    int decrLikeCountById(Long postId);

    int incrCommentCountById(Long postId);

    int decrCommentCountById(Long postId);

    PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn, PageBounds pageBounds);


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

    /**
     *获取验证投票信息
     * @param memberSn
     * @param postId
     * @return map{isVote=1, effective_time=2016-01-15 18:06:36.0}
     */
    Map<String,Object> getVerifyVoteInfo(@Param("memberSn")String memberSn, @Param("postId")Long postId);

    /**
     * 投票
     * @param votePostParam
     * @return
     */
    int votePost(VotePostParam votePostParam);

    /**
     * 获取投票帖投票结果
     * @param postId
     * @return
     */
    VotePostResult getVoteResultByPostId(Long postId);

    /**
     * 更新投票帖 投票总数
     * @param voteCount
     * @param postId
     * @return
     */
    int updatePostVoteCount(@Param("voteCount")Integer voteCount, @Param("postId")Long postId);

    /**
     * 根据fandomId, memberSn分页查询所有投票帖
     * @param fandomId
     * @param memberSn
     * @param mySn
     * @param pageBounds
     * @return
     */
    PageList<PostInfoResult> listVoteMemberFandomPosts(@Param("fandomId") long fandomId, @Param("memberSn") String memberSn, @Param("mySn") String mySn, PageBounds pageBounds);
}