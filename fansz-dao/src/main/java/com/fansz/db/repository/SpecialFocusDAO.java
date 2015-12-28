package com.fansz.db.repository;

import com.fansz.db.entity.SpecialFocus;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;

/**
 * Created by allan on 15/12/28.
 */
@DAO("specialFocusDAO")
public interface SpecialFocusDAO extends IBaseDAO<SpecialFocus> {
    /**
     * 删除特别关注关联信息
     * @return
     */
    @NamedExec(execId="specialfocus.delSpecialFocusInfo",parameters = {"currentSn","specialMemberSn","specialFandomId"})
    int delSpecialFocusInfo(String currentSn,String specialMemberSn,Long specialFandomId);
}
