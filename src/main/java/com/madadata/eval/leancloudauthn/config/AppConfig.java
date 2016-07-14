package com.madadata.eval.leancloudauthn.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.madadata.eval.leancloudauthn.client.LeancloudClient;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;

/**
 * Created by jiayu on 7/14/16.
 */
public class AppConfig extends Configuration {

    @NotEmpty
    private final String authCachingPolicy;

    @Valid
    @NotNull
    private final LeancloudConfig leancloud;

    @Valid
    @NotNull
    private final JerseyClientConfiguration jerseyClient;

    @JsonCreator
    public AppConfig(@JsonProperty("authCachingPolicy") String authCachingPolicy,
                     @JsonProperty("leancloud") LeancloudConfig leancloud,
                     @JsonProperty("jerseyClient") JerseyClientConfiguration jerseyClient) {
        this.authCachingPolicy = authCachingPolicy;
        this.leancloud = leancloud;
        this.jerseyClient = jerseyClient;
    }

    @JsonProperty("authCachingPolicy")
    public String getAuthCachingPolicy() {
        return authCachingPolicy;
    }

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("leancloud")
    public LeancloudConfig getLeancloud() {
        return leancloud;
    }


    public LeancloudClient buildLeancloudClient(Environment environment) {
        final Client client = new JerseyClientBuilder(environment)
                .using(getJerseyClientConfiguration())
                .build("LeancloudClient");
        return new LeancloudClient(client, leancloud);
    }
}
