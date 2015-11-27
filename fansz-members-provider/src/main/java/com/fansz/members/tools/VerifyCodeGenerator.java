package com.fansz.members.tools;


import java.text.DecimalFormat;

/**
 * 验证码产生器
 */
public class VerifyCodeGenerator {

    public static final int CODE_LENGTH = 6;

    private int length;

    private int factor;

    private DecimalFormat df;

    public VerifyCodeGenerator() {
        length = CODE_LENGTH;
        init();
    }

    public VerifyCodeGenerator(int length) {
        this.length = length;
        init();
    }

    private void init() {
        //Random factor
        factor = (int) Math.pow(10, length) - 1;
        //Code pattern
        StringBuilder pattern = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            pattern.append("0");
        }
        df = new DecimalFormat(pattern.toString());
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getIdentifyCode() {
        return df.format((int) (Math.random() * factor));

    }
}
