package com.fansz.feeds;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/12/24.
 */
public class FeedStarter {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-feeds.xml");
        ac.start();
    }
}
