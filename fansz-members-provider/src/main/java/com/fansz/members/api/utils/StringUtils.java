package com.fansz.members.api.utils;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.Fandom;
import com.fansz.members.model.Friendship;
import com.fansz.members.model.Post;
import com.fansz.members.model.User;
import com.fansz.members.model.param.PagePara;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class StringUtils {

    // get client real IP
    public static String getRemoteIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // MD5 hash
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // XSS filter
    public static String getSearchWord(String word) {
        word = word.trim();
        word = word.replace('<', ' ');
        word = word.replace('>', ' ');
        word = word.replace('"', ' ');
        word = word.replace('\'', ' ');
        word = word.replace('/', ' ');
        word = word.replace('%', ' ');
        word = word.replace(';', ' ');
        word = word.replace('(', ' ');
        word = word.replace(')', ' ');
        word = word.replace('&', ' ');
        word = word.replace('+', '_');
        return word;
    }

    public static boolean isNotNull(String str)
    {
        if (str != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public static boolean isEmpty(String str)
    {
        if (str == null || "".equals(str))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isEmpty(List<String> str)
    {
        if (str == null || str.size() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isEmptyAndLen(String str, int length)
    {
        if (str == null || "".equals(str) || str.length() > length)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean checkLen(String str, int length)
    {
        if (str != null && str.length() > length)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public static int getRandomNum()
    {
        Random random = new Random();
        return random.nextInt(899999)+100000;
    }

    public static boolean isNumber(String str)
    {
        boolean result;
        try {
            Integer.parseInt(str);
            result = true;
        } catch (Exception e)
        {
            result = false;
        }

        return result;
    }

    public static Response getErrorResponse(Vector<ErrorMessage> errorMessages)
    {
        UnifyResponse<List<ErrorMessage>> unifyResponse = new UnifyResponse<List<ErrorMessage>>();

        unifyResponse.setCode(errorMessages.get(0).getReturnCode());
        unifyResponse.setInfo(errorMessages.get(0).getErrorMsg());
        unifyResponse.setAfterCount(0);
        unifyResponse.setContent(null);

        return Response.status(418).entity(unifyResponse).build();
    }

    public static Response getSuccessResponse(int afterCount, Object obj)
    {
        UnifyResponse<Object> unifyResponse = new UnifyResponse<>();
        unifyResponse.setCode("0");
        unifyResponse.setInfo("success");
        unifyResponse.setAfterCount(afterCount);
        unifyResponse.setContent(obj);

        return Response.ok(unifyResponse).build();
    }

    public static String getShip(String id, Friendship myFriendShip) {

        String ship = Constants.FRIENDS_STATUS_NOT_KNOW;

        if (null != myFriendShip)
        {
            if (null != myFriendShip.getFriends())
            {
                for (User user : myFriendShip.getFriends())
                {
                    if (id.equals(user.getId()))
                    {
                        ship = Constants.FRIENDS_STATUS_FRIEND;
                        break;
                    }
                }
            }

            if (null != myFriendShip.getCandidates()) {
                for (User user : myFriendShip.getCandidates()) {
                    if (id.equals(user.getId())) {
                        ship = Constants.FRIENDS_STATUS_FOLLOW_ME;
                        break;
                    }
                }
            }

            if (null != myFriendShip.getRequesters())
            {
                for (User user : myFriendShip.getRequesters()) {
                    if (id.equals(user.getId()))
                    {
                        ship = Constants.FRIENDS_STATUS_FOLLOW;
                        break;
                    }
                }
            }

            if (id.equals(myFriendShip.getUserId()))
            {
                ship = Constants.FRIENDS_STATUS_MYSELF;
            }
        }
        return ship;
    }

    public static void setFollowedFandom(List<Fandom> fandoms, List<String> fandomIds) {

        for (Fandom fandom : fandoms)
        {
            if (fandomIds.contains(fandom.getId()))
            {
                fandom.setFollowed("1");
            }
            else
            {
                fandom.setFollowed("0");
            }
        }
    }

    public static PagePara setPageParam(PagePara pagePara) {

        int start = Constants.PAGE_BEGIN_DEFAULT;
        int count = Constants.PAGE_SIZE_DEFAULT;

        // 起始点默认为0,返回条数默认20,大于100的默认为100
        if (pagePara != null)
        {
            if (pagePara.getPageStart() > Constants.PAGE_BEGIN_DEFAULT )
            {
                start = pagePara.getPageStart();
            }

            if (pagePara.getCount() > 0 && pagePara.getCount() <= Constants.PAGE_BEGIN_OVER)
            {
                count = pagePara.getCount();
            }
            else if (pagePara.getCount() > Constants.PAGE_BEGIN_OVER)
            {
                count = Constants.PAGE_BEGIN_OVER;
            }
        }
        else
        {
            pagePara = new PagePara();
        }

        pagePara.setPageStart(start);
        pagePara.setCount(count);

        return pagePara;
    }

    public static Integer changeIdType(String id)
    {
        return Integer.parseInt(id);
    }

    // 转换fandom实体数据
    public static Fandom changeFandom(FandomEntity fandomEntity)
    {
        Fandom fandom = new Fandom();
        fandom.setTitle(fandomEntity.getFandomName());
        fandom.setAvatar(fandomEntity.getFandomAvatarUrl());
        fandom.setCategoryId(fandomEntity.getFandomParentId().toString());
        fandom.setDescription(fandomEntity.getFandomIntro());

        return fandom;
    }

    // 批量转换fandom实体数据
    public static List<Fandom> changeFandoms(List<FandomEntity> fandomEntity)
    {
        List<Fandom> fandoms = new ArrayList<Fandom>();

        Fandom fandom = null;
        for (FandomEntity entity : fandomEntity)
        {
            fandom = new Fandom();
            fandom.setTitle(entity.getFandomName());
            fandom.setAvatar(entity.getFandomAvatarUrl());
            fandom.setCategoryId(entity.getFandomParentId().toString());
            fandom.setDescription(entity.getFandomIntro());
            fandoms.add(fandom);
        }

        return fandoms;
    }

    // fandom数据转换
    public static Post changeFandom(FandomPostEntity fandomEntity)
    {
        Post post = new Post();
        post.setId(fandomEntity.getId().toString());

        List<String> fandoms = new ArrayList<>();
        fandoms.add(fandomEntity.getFandomId().toString());
        post.setFandoms(fandoms);

        post.setContent(fandomEntity.getPostContent());
        post.setCreateTime(fandomEntity.getPostTime());

        return post;
    }

    // 转换用户信息
    public static User changeUser(UserEntity entity)
    {
        User user = new User();
        user.setId(entity.getId().toString());
        user.setMobile(entity.getMobile());
        user.setLoginName(entity.getLoginname());
        user.setAvatar(entity.getMemberAvatar());
        user.setCreateTime(entity.getProfileCreatetime());
        user.setNickName(entity.getNickname());
        user.setGender(entity.getGender());

        return user;
    }

    // 批量转换用户信息
    public static List<User> changeUsers(List<UserEntity> entitys) {

        List<User> users = new ArrayList<>();
        if (entitys != null && entitys.size() > 0)
        {
            for (UserEntity userEntity : entitys)
            {
                users.add(changeUser(userEntity));
            }
        }

        return users;
    }
}