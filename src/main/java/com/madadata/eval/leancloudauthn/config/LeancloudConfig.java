package com.madadata.eval.leancloudauthn.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudConfig {

    @NotEmpty
    private final String authUrl;
    @NotEmpty
    private final String appId;
    @NotEmpty
    private final String appKey;

    @JsonCreator
    public LeancloudConfig(@JsonProperty("authUrl") String authUrl,
                           @JsonProperty("appId") String appId,
                           @JsonProperty("appKey") String appKey) {
        this.authUrl = authUrl;
        this.appId = appId;
        this.appKey = appKey;
    }

    @JsonProperty
    public String getAuthUrl() {
        return authUrl;
    }

    @JsonProperty
    public String getAppId() {
        return appId;
    }

    @JsonProperty
    public String getAppKey() {
        return appKey;
    }
}
