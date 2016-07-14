package com.madadata.eval.leancloudauthn.health;

import com.codahale.metrics.health.HealthCheck;
import com.madadata.eval.leancloudauthn.client.LeancloudClient;

import javax.ws.rs.core.Response;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudHealthCheck extends HealthCheck {

    private final LeancloudClient leancloudClient;

    public LeancloudHealthCheck(LeancloudClient leancloudClient) {
        this.leancloudClient = leancloudClient;
    }

    @Override
    protected Result check() throws Exception {
        Response response = leancloudClient.getDate();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return Result.healthy(String.valueOf(response.getStatusInfo()));
        } else {
            return Result.unhealthy(response.getStatusInfo().toString());
        }
    }
}
