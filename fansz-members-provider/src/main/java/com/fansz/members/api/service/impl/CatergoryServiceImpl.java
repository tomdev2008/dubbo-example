package com.fansz.members.api.service.impl;

import com.fansz.members.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by root on 15-11-11.
 */
@Service
public class CatergoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    FandomMapper fandomMapper;

    @Autowired
    ProfileService profileMapper;
    @Autowired
    private FileService fileService;

    @Value("${pictures.base}")
    private String PICTURE_BASH_PATH;

    @Override
    public Category addCategory(CategoryParam categoryParam)  throws IOException {

        InputStream fileInputStream = null;
        String source = null;
        String fileName = null;
        FormDataContentDisposition formDataContentDisposition;

        Category category = new Category(categoryParam);

//        if (categoryParam.getAvatar() != null && categoryParam.getAvatar().getName() != null)
//        {
//            //Save Avatar
//            fileInputStream = categoryParam.getAvatar().getValueAs(InputStream.class);
//            //Get FileName
//            formDataContentDisposition = categoryParam.getAvatar().getFormDataContentDisposition();
//            source = formDataContentDisposition.getFileName();
//            source = new String(source.getBytes("ISO8859-1"), "UTF-8");
//
//            //Generate File name
//            fileName = fileService.fileUpload(PICTURE_BASH_PATH,fileInputStream,source);
//            category.setAvatar(fileName);
//        }

        //Save category
        categoryMapper.save(category);

        return category;
    }

    @Override
    public SubCategory addSubCategory(CategoryParam categoryParam) throws IOException
    {
        SubCategory subCategory = new SubCategory(categoryParam);

        InputStream fileInputStream = null;
        String source = null;
        String fileName = null;
        FormDataContentDisposition formDataContentDisposition;

//        if (categoryParam.getAvatar() != null && categoryParam.getAvatar().getName() != null)
//        {
//            //Save Avatar
//            fileInputStream = categoryParam.getAvatar().getValueAs(InputStream.class);
//            //Get FileName
//            formDataContentDisposition = categoryParam.getAvatar().getFormDataContentDisposition();
//            source = formDataContentDisposition.getFileName();
//            source = new String(source.getBytes("ISO8859-1"), "UTF-8");
//
//            //Generate Random File name
//            fileName = fileService.fileUpload(PICTURE_BASH_PATH,fileInputStream,source);
//            subCategory.setAvatar(fileName);
//        }

        categoryMapper.saveSubCategory(subCategory);
        return subCategory;
    }

    @Override
    public Category getCategoryById(String id) {
        Category category = categoryMapper.getCategory(id);
        List<SubCategory> subCategories = categoryMapper.getSubCategorysById(id);
        List<Fandom> fandoms = null;

        if (subCategories != null && subCategories.size() > 0)
        {
            for (SubCategory subCategory : subCategories)
            {
                fandoms = fandomMapper.getFandomsByCategoryIdPage(subCategory.getId(), 0, 3);
                subCategory.setFandoms(fandoms);
            }
        }
        category.setSubCategories(subCategories);
        return category;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    @Override
    public SubCategory getFandomsBySubCategory(User user, String id) {

        SubCategory subCategory =  categoryMapper.getFandomsBySubCategory(id);
        List<Fandom> fandoms =  subCategory.getFandoms();

        // 设置我是否已关注这些fandom
        if (null != fandoms)
        {
            User userNew = profileMapper.getProfile(user.getId());
            if (null != userNew && !StringUtils.isEmpty(userNew.getFandomIds()))
            {
                StringUtils.setFollowedFandom(fandoms, userNew.getFandomIds());
            }
        }

        subCategory.setFandoms(fandoms);
        return subCategory;

    }

}
