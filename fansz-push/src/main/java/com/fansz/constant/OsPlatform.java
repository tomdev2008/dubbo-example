package com.fansz.constant;

public enum OsPlatform {
    ANDROID("android"), IOS("ios");

    private String name;

    private OsPlatform(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static OsPlatform getByName(String name) {
        for (OsPlatform p : OsPlatform.values()) {
            if (p.getName().equals(name))
                return p;
        }
        throw new IllegalArgumentException(String.format("No OsPlatform found with name %d", name));
    }
}
