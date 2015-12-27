package com.fansz.db.repository;

import com.fansz.db.entity.PushPost;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;

/**
 * Created by allan on 15/12/26.
 */
@DAO("pushPostDAO")
public interface PushPostDAO extends IBaseDAO<PushPost> {
}
