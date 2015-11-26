package com.fansz.members.model.param;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

/**
 * Created by root on 15-11-4.
 */
@Data
@NoArgsConstructor
public class FandomParam {

    @Length(max = 200, message = "error.title.over")
    private String title;

    @Length(max = 10, message = "error.categoryId.over")
    private String categoryId;

    @Length(max = 500, message = "error.description.over")
    private String description;

    private FormDataBodyPart avatar;

    private FormDataBodyPart backImage;

    public FandomParam(FormDataMultiPart form) {
        if (form.getFields().containsKey("title"))
        {
            this.title = form.getField("title").getValue();
        }

        if (form.getFields().containsKey("categoryId"))
        {
            this.categoryId = form.getField("categoryId").getValue();
        }
        Assert.isTrue(form.getFields().containsKey("categoryId"), "error.category.id");

        if (form.getFields().containsKey("description"))
        {
            this.description = form.getField("description").getValue();
        }

        Assert.isTrue(form.getFields().containsKey("title") || form.getFields().containsKey("description"), "error.fandom.empty");

        this.avatar = form.getField("avatar");
        this.backImage = form.getField("backImage");
    }
}
