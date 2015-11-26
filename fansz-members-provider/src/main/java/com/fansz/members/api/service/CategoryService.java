package com.fansz.members.api.service;

import com.fansz.appservice.persistence.domain.Category;
import com.fansz.appservice.persistence.domain.SubCategory;
import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.resource.param.CategoryParam;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface CategoryService {
    Category addCategory(CategoryParam categoryParam) throws IOException;

    SubCategory addSubCategory(CategoryParam categoryParam) throws IOException;

    Category getCategoryById(
            @NotEmpty(message = "error.category.id")
            String id);

    List<Category> getAllCategory();

    SubCategory getFandomsBySubCategory(
            User user,
            @NotEmpty(message = "error.category.id")
            String id);
}
