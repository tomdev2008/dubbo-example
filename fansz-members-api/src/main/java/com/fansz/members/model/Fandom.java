package com.fansz.members.model;

import com.fansz.appservice.resource.param.FandomParam;
import com.fansz.appservice.utils.ShortUUIDGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Data
@NoArgsConstructor
@Document(collection = "fandom")
public class Fandom {
    private String id;
    private String title;
    private String description;
    private String avatar;
    private String backImage;
    private String categoryId;
    private int followCount;
    private int postsCount;
    private String followed = "0";
    private List<User> followers;
    private User owner;
    private List<Post> posts;
}
