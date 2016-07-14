package com.madadata.eval.leancloudauthn.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by jiayu on 7/14/16.
 */
public class AppConfig extends Configuration {

    @Valid
    @NotNull
    private final LeancloudConfig leancloudConfig;

    @Valid
    @NotNull
    private final JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonCreator
    public AppConfig(@JsonProperty("leancloudConfig") LeancloudConfig leancloudConfig) {
        this.leancloudConfig = leancloudConfig;
    }

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("leancloudConfig")
    public LeancloudConfig getLeancloudConfig() {
        return leancloudConfig;
    }
}
