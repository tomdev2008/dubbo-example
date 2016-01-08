package com.fansz.db.repository;

import com.fansz.db.entity.User;
import com.fansz.db.model.FriendInfo;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;

import java.util.List;

/**
 * Created by allan on 15/12/23.
 */
@DAO("userDAO")
public interface UserDAO extends IBaseDAO<User> {
    @NamedQuery(queryId = "user.findByMobile",parameters = "mobile")
    User findByMobile(String mobile);

    @NamedQuery(queryId = "user.findBySn",parameters = "sn")
    User findBySn(String sn);

    @NamedQuery(queryId = "user.findByAccoount",parameters = "loginname")
    User findByAccount(String loginname);

    @NamedExec(execId = "user.updatePassword", parameters = {"userId", "password"})
    int updatePassword(Long id, String password);

    @NamedQuery(queryId = "user.findBySnList", parameters = {"memberSnList"})
    List<User> findBySnString(List<String> memberSnList);

    @NamedQuery(queryId = "user.findByMobiles", parameters = {"page","mobileList"})
    QueryResult<User> findByMobiles(Page page, List<String> mobileList);


}
