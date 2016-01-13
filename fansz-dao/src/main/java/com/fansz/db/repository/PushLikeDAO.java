package com.fansz.db.repository;

import com.fansz.db.entity.PushLike;
import com.fansz.orm.dao.IBaseDAO;
import org.springframework.stereotype.Component;

/**
 * Created by allan on 16/1/12.
 */
@Component("pushLikeDAO")
public interface PushLikeDAO extends IBaseDAO<PushLike> {
}
