package com.fansz.auth.repository;

import com.fansz.auth.entity.UserEntity;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;

/**
 * Created by allan on 15/12/23.
 */
@DAO("userRepository")
public interface UserRepository extends IBaseDAO<UserEntity> {
    @NamedQuery(queryId = "user.findByMobile",parameters = "mobile")
    UserEntity findByMobile(String mobile);

    @NamedQuery(queryId = "user.findBySn",parameters = "sn")
    UserEntity findBySn(String sn);

    @NamedQuery(queryId = "user.findByAccoount",parameters = "loginname")
    UserEntity findByAccount(String loginname);

    @NamedExec(execId = "user.updatePassword", parameters = {"userId", "password"})
    int updatePassword(Long id, String password);
}
