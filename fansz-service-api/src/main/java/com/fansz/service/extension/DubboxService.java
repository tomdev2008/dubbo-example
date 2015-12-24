package com.fansz.service.extension;

import java.lang.annotation.*;

/**
 * Created by allan on 15/12/14.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DubboxService {
    String value();
}
