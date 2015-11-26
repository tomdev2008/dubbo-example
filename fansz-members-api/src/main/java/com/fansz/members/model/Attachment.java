package com.fansz.members.model;

import com.fansz.appservice.utils.ShortUUIDGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by root on 15-11-4.
 */
@Data
@AllArgsConstructor
public class Attachment {
    private String id = ShortUUIDGenerator.generate();;
    private String content; //file name
    private String type;//image, audio, movie
    private int order;
}
