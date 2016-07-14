package com.madadata.eval.leancloudauthn.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudConfig {

    @NotEmpty
    private final String baseUrl;
    @NotEmpty
    private final String appId;
    @NotEmpty
    private final String appKey;

    @JsonCreator
    public LeancloudConfig(@JsonProperty("authUrl") String baseUrl,
                           @JsonProperty("appId") String appId,
                           @JsonProperty("appKey") String appKey) {
        this.baseUrl = baseUrl;
        this.appId = appId;
        this.appKey = appKey;
    }

    @JsonProperty
    public String getBaseUrl() {
        return baseUrl;
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
