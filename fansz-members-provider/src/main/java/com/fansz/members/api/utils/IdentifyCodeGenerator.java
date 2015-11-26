package com.fansz.members.api.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;

/**
 * Hi 功能函数
 *
 * Created by xuzhen on 15/6/2.
 */
@Configuration
public class IdentifyCodeGenerator implements InitializingBean {

    public static final int CODE_LENGTH = 6;

    private int length;

    private int factor;

    private DecimalFormat df;

    public IdentifyCodeGenerator() {
        length = CODE_LENGTH;
    }

    public IdentifyCodeGenerator(int length) {
        this.length = length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getIdentifyCode() {
        //Get Code
        return df.format((int)(Math.random() * factor));

    }

    @Bean
    public IdentifyCodeGenerator identifyCodeGenerator() {
        IdentifyCodeGenerator identifyCodeGenerator = new IdentifyCodeGenerator();
        return identifyCodeGenerator;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Random factor
        factor = (int)Math.pow(10, length) - 1;
        //Code pattern
        StringBuilder pattern = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            pattern.append("0");
        }
        df = new DecimalFormat(pattern.toString());
    }

    public static void main(String[] args) throws Exception {
        IdentifyCodeGenerator identifyCodeGenerator = new IdentifyCodeGenerator();
        identifyCodeGenerator.afterPropertiesSet();
        System.out.println(identifyCodeGenerator.getIdentifyCode());
    }
}
