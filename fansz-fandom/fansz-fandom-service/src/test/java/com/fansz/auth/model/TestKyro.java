package com.fansz.auth.model;

import com.fansz.common.provider.model.PageParam;
import com.fansz.fandom.model.post.PostInfoResult;
import com.fansz.pub.utils.JsonHelper;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.Date;

/**
 * Created by allan on 15/12/28.
 */
public class TestKyro {
    public static void main(String[] args) {
       /* Long now=System.currentTimeMillis();
      String a=String.format("{\"date\":\"%d\"}",now);
        TestModel model=JsonHelper.convertJSONString2Object(a,TestModel.class);
        System.out.println(now);*/
        byte[] a={48,48,48,51};
        System.out.println(new String(a));
    }

}
