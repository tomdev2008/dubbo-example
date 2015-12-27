package com.fansz.db.repository;

import com.fansz.db.entity.FandomPost;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;

/**
 * Created by allan on 15/12/25.
 */
@DAO("fandomPostDAO")
public interface FandomPostDAO extends IBaseDAO<FandomPost> {
}
