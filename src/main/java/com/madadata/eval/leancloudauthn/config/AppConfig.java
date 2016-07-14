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
    private final LeancloudConfig leancloud;

    @Valid
    @NotNull
    private final JerseyClientConfiguration jerseyClient;

    @JsonCreator
    public AppConfig(@JsonProperty("leancloud") LeancloudConfig leancloud,
                     @JsonProperty("jerseyClient") JerseyClientConfiguration jerseyClient) {
        this.leancloud = leancloud;
        this.jerseyClient = jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("leancloudConfig")
    public LeancloudConfig getLeancloud() {
        return leancloud;
    }
}
