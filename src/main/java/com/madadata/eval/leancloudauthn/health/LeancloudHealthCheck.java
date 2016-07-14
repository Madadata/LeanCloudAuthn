package com.madadata.eval.leancloudauthn.health;

import com.codahale.metrics.health.HealthCheck;
import com.madadata.eval.leancloudauthn.config.LeancloudConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudHealthCheck extends HealthCheck {

    private final Client client;
    private final LeancloudConfig leancloudConfig;

    public LeancloudHealthCheck(Client client, LeancloudConfig leancloudConfig) {
        this.client = client;
        this.leancloudConfig = leancloudConfig;
    }

    @Override
    protected Result check() throws Exception {
        URI dateCheckUri = UriBuilder
                .fromPath(leancloudConfig.getBaseUrl())
                .path("date")
                .build();
        Response response = client.target(dateCheckUri).request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return Result.healthy(String.valueOf(response.getStatusInfo()));
        } else {
            return Result.unhealthy(response.getStatusInfo().toString());
        }
    }
}
