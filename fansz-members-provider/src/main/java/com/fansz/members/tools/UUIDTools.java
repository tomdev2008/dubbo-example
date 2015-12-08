package com.fansz.members.tools;

import java.util.UUID;

/**
 * Created by allan on 15/11/22.
 */
public final class UUIDTools {
    public static String getUniqueId(){
       return UUID.randomUUID().toString().replace("-","");
    }
}
