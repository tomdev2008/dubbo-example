package com.fansz.db.repository;

import com.fansz.db.entity.PushComment;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;

/**
 * Created by allan on 16/1/12.
 */
@DAO("pushCommentDAO")
public interface PushCommentDAO extends IBaseDAO<PushComment> {
}
