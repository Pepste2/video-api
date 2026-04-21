/*
 * Decompiled with CFR 0.152.
 */
package com.hikvision.artemis.sdk.config;

public class ArtemisConfig {
    public static String host;
    public static String appKey;
    public static String appSecret;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        ArtemisConfig.host = host;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        ArtemisConfig.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        ArtemisConfig.appSecret = appSecret;
    }
}
