package com.fansz.members.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by root on 15-11-3.
 */
@Data
@Document(collection = "comment")
@NoArgsConstructor
public class Comment {

    private String id;

    private String postId;

    private String content;

    private User user;

    private Date createTime;

    private boolean enable = true;
}
