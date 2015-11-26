package com.fansz.members.api.provider;

import com.fansz.appservice.persistence.domain.SubCategory;
import com.fansz.appservice.resource.param.CategoryParam;
import com.fansz.appservice.service.CategoryService;
import com.fansz.appservice.utils.ErrorMessage;
import com.fansz.appservice.utils.ErrorParser;
import com.fansz.appservice.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Vector;

/**
 * fandom类别接口类
 * Created by root on 15-11-3.
 */
@Service
@Component("/categoryProvider")
public class CategoryProvider {

    @Autowired
    private ErrorParser errorParser;

    @Autowired
    private CategoryService categoryService;


    /**
     * 添加圈子类别接口
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Produces("application/json")
    public Response addCategory(CategoryParam categoryParam)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();

        try {
            categoryService.addCategory(categoryParam);
        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, null);
        }
    }

    /**
     * 添加圈子子类别接口
     * @return resp 返回对象
     */
    @POST
    @Path("/add/subCategory")
    @Produces("application/json")
    public Response addSubCategory(CategoryParam categoryParam)
    {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        SubCategory subCategory = null;
        try {
            subCategory = categoryService.addSubCategory(categoryParam);
        } catch (IllegalArgumentException iae) {
            errorMessages.add(errorParser.phase(iae.getMessage()));
        } catch (ConstraintViolationException cve) {
            errorMessages.addAll(errorParser.phase(cve.getConstraintViolations()));
        } catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }
        if (errorMessages.size() != 0) {
            return StringUtils.getErrorResponse(errorMessages);
        }
        else {
            return StringUtils.getSuccessResponse(0, subCategory);
        }
    }
}
