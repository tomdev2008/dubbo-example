package com.fansz.db.repository;

import com.fansz.db.entity.PushLike;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;

/**
 * Created by allan on 16/1/12.
 */
@DAO("pushLikeDAO")
public interface PushLikeDAO extends IBaseDAO<PushLike> {
}
