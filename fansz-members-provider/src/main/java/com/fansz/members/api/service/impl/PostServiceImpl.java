package com.fansz.members.api.service.impl;

import com.fansz.appservice.persistence.domain.Attachment;
import com.fansz.appservice.persistence.domain.Fandom;
import com.fansz.appservice.persistence.domain.Post;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.persistence.mapper.FandomMapper;
import com.fansz.appservice.persistence.mapper.FriendsMapper;
import com.fansz.appservice.persistence.mapper.PostMapper;
import com.fansz.appservice.resource.param.PagePara;
import com.fansz.appservice.resource.param.PostParam;
import com.fansz.appservice.service.FileService;
import com.fansz.appservice.service.PostService;
import com.fansz.appservice.service.ProfileService;
import com.fansz.appservice.utils.FileOperator;
import com.fansz.appservice.utils.ShortUUIDGenerator;
import com.fansz.appservice.utils.StringUtils;
import com.mongodb.WriteResult;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by root on 15-11-3.
 */
@Service
public class PostServiceImpl implements PostService {

    @Value("${pictures.base}")
    private String PICTURE_BASE_PATH;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FileService fileService;

    @Override
    public Post addPost(User user, PostParam postParam) throws IOException {

        String source = null;
        String fileName;
        List<Attachment> attachments = new Vector<>();
        int index = 0;

        //Upload Files
        for (FormDataBodyPart formDataBodyPart : postParam.getAttachments()) {

            //获取文件IO流
            InputStream fileInputStream = formDataBodyPart.getValueAs(InputStream.class);

            //获取文件名
            FormDataContentDisposition formDataContentDisposition = formDataBodyPart.getFormDataContentDisposition();
            source = new String(formDataContentDisposition.getFileName().getBytes("ISO8859-1"), "UTF-8");

            //写文件流
            fileName = fileService.fileUpload(PICTURE_BASE_PATH, fileInputStream, source);

            //保存fileName
            attachments.add(new Attachment(ShortUUIDGenerator.generate(), fileName, FileOperator.getExtensionName(fileName), ++index));
        }

        Post post = new Post(user, postParam, attachments);

        postMapper.addPost(post);

        // 给fandom的post总数适当增加1
        if (post.getFandoms() != null && post.getFandoms().size() > 0)
        {
            for (String fandomId : post.getFandoms())
            {
                fandomMapper.modifyPostsCount(fandomId, 1);
            }
        }

        return post;
    }

    @Override
    public void removePost(String id) {
        Post post = postMapper.getPost(id);
        postMapper.removePost(id);
        if (post != null && post.getFandoms() != null && post.getFandoms().size() > 0)
        {
            for (String fandomId : post.getFandoms())
            {
                fandomMapper.modifyPostsCount(fandomId, -1);
            }
        }
    }

    @Override
    public Post getPost(User user, String id) {
        Post post = postMapper.getPost(id);

        if (post != null)
        {
            post.setLiked(postMapper.islikedPost(id, user));
        }
        return post;
    }

    @Override
    public void likePost(User user, String id) {

        WriteResult writeResult = postMapper.likePost(id, user);

    }

    @Override
    public void unlikePost(User user, String id) {
        WriteResult writeResult = postMapper.unlikePost(id, user);
    }

    @Override
    public List<Post> getFriendPosts(User user, String friendId) {
        List<Post> posts = postMapper.getFriendPosts(friendId);

        if (posts != null && posts.size() > 0)
        {
            for (Post post : posts)
            {
                post.setLiked(postMapper.islikedPost(post.getId(), user));
            }
        }
        return posts;
    }

    @Override
    public List<Post> getFriendsPosts(User user, PagePara pagePara) {

        List<User> friends = friendsMapper.getFriends(user.getId());

        List<String> ids = new ArrayList<String>();

        if (null != friends && friends.size() > 0)
        {
            for (User friend : friends)
            {
                ids.add(friend.getId());
            }
        }

        ids.add(user.getId());

        PagePara pageParam = StringUtils.setPageParam(pagePara);

        // 查询好友发布的帖子，分页返回
        List<Post> posts = postMapper.getFriendsPosts(ids, pageParam);

        if (posts != null && posts.size() > 0)
        {
            for (Post post : posts)
            {
                post.setLiked(postMapper.islikedPost(post.getId(), user));
            }
        }
        return posts;
    }

    @Override
    public List<Post> getAllFandomPosts(User user, PagePara pagePara)
    {
        User me = profileService.getProfile(user.getId());

        List<String> fandoms = me.getFandomIds();
        List<Fandom> fandomList = fandomMapper.getFandomsByIds(fandoms);
        Map<String, Fandom> fandomMap = new HashMap();

        for (Fandom fandom : fandomList)
        {
            fandomMap.put(fandom.getId(), new Fandom(fandom));
        }

        PagePara pageParam = StringUtils.setPageParam(pagePara);

        List<Post> posts = postMapper.getFandomsPosts(fandoms, pageParam);

        List<String> fandomIds = new ArrayList<>();

        // 设置post来源于哪个fandom，来自很多个默认取第一个
        for (Post post : posts)
        {
            fandomIds.clear();
            fandomIds.addAll(fandoms);
            fandomIds.retainAll(post.getFandoms());

            if (fandomIds != null && fandomIds.size() > 0)
            {
                post.setSource(fandomMap.get(fandomIds.get(0)));
            }
        }

        return posts;
    }

}
