package com.fansz.db.repository;

import com.fansz.db.entity.User;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;

/**
 * Created by allan on 15/12/23.
 */
@DAO("userRepository")
public interface UserDAO extends IBaseDAO<User> {
    @NamedQuery(queryId = "user.findByMobile",parameters = "mobile")
    User findByMobile(String mobile);

    @NamedQuery(queryId = "user.findBySn",parameters = "sn")
    User findBySn(String sn);

    @NamedQuery(queryId = "user.findByAccoount",parameters = "loginname")
    User findByAccount(String loginname);

    @NamedExec(execId = "user.updatePassword", parameters = {"userId", "password"})
    int updatePassword(Long id, String password);
}
