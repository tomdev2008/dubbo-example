package com.fansz.db.repository;

import com.fansz.db.entity.NewsfeedsPostEntity;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;

/**
 * Created by allan on 15/12/25.
 */
@DAO("newsfeedsPostDAO")
public interface NewsfeedsPostDAO extends IBaseDAO<NewsfeedsPostEntity> {
}
