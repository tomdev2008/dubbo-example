package com.fansz.members.model.param;

import com.fansz.appservice.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 *
 *
 * Created by root on 15-11-3.
 */
@Data
@NoArgsConstructor
public class PostParam {

    @Length(max = 200, message = "error.title.over")
    private String title;

    @Length(max = 500, message = "error.content.over")
    private String content;

    @Length(max = 10, message = "error.type.over")
    private String type;

    @Length(max = 20, message = "error.direction.over")
    private String direction;

    private List<String> fandoms = new Vector<>();

    private List<FormDataBodyPart> attachments = new Vector<>();

    public PostParam(FormDataMultiPart form) throws UnsupportedEncodingException {

        if (form.getFields().containsKey("title"))
            this.title = form.getField("title").getValue();

        Assert.isTrue(form.getFields().containsKey("content") || form.getFields().containsKey("file"), "error.post.empty");

        if (form.getFields().containsKey("content"))
            this.content = form.getField("content").getValue();

        this.type = form.getField("type").getValue();

        this.direction = form.getField("direction").getValue();

        if (form.getFields().containsKey("fandom")) {
//            for (FormDataBodyPart formDataBodyPart : form.getFields("fandom")) {
//                if (null != formDataBodyPart.getValue())
//                    this.fandoms.add(formDataBodyPart.getValue());
//            }
            String fandoms = form.getField("fandom").getValue();

            if (!StringUtils.isEmpty(fandoms))
            {
                String[] fandom = fandoms.split(",");
                this.fandoms = Arrays.asList(fandom);
            }

        }

        if (form.getFields().containsKey("file")) {
            for (FormDataBodyPart formDataBodyPart : form.getFields("file")) {
                FormDataContentDisposition formDataContentDisposition = formDataBodyPart.getFormDataContentDisposition();
                String source = new String(formDataContentDisposition.getFileName().getBytes("ISO8859-1"), "UTF-8");
                if (!source.isEmpty())
                    this.attachments.add(formDataBodyPart);
            }
        }
    }
}
