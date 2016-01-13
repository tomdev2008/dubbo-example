package com.fansz.db.repository;

import com.fansz.db.entity.Fandom;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;

/**
 * Created by allan on 16/1/13.
 */
@DAO("fandomDAO")
public interface FandomDAO extends IBaseDAO<Fandom> {
}
