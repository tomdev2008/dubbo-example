package com.fansz.members.api.service;

import com.fansz.members.model.SubCategory;
import com.fansz.members.model.User;
import com.fansz.members.model.param.CategoryParam;
import com.fansz.members.model.Category;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface CategoryService {
    Category addCategory(CategoryParam categoryParam) throws IOException;

    SubCategory addSubCategory(CategoryParam categoryParam) throws IOException;

    Category getCategoryById(String id);

    List<Category> getAllCategory();

    SubCategory getFandomsBySubCategory(String myId,String id);
}
