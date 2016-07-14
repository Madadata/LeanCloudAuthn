package com.madadata.eval.leancloudauthn.client;

import com.madadata.eval.leancloudauthn.api.UserInfo;
import com.madadata.eval.leancloudauthn.config.LeancloudConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static java.util.Objects.requireNonNull;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudClient {

    private final Client client;
    private final LeancloudConfig leancloudConfig;

    public LeancloudClient(Client client, LeancloudConfig leancloudConfig) {
        this.client = requireNonNull(client);
        this.leancloudConfig = requireNonNull(leancloudConfig);
    }

    public Response getDate() {
        URI dateCheckUri = UriBuilder
                .fromPath(leancloudConfig.getBaseUrl())
                .path("date")
                .build();
        return client.target(dateCheckUri).request().get();
    }

    public UserInfo getUserInfoById(String userId) {
        URI uri = UriBuilder.fromUri(leancloudConfig.getBaseUrl())
                .path("users")
                .path(userId)
                .build();
        return client.target(uri)
                .request()
                .header("X-LC-Id", leancloudConfig.getAppId())
                .header("X-LC-Key", leancloudConfig.getAppKey())
                .get(UserInfo.class);
    }

    public UserInfo getUserInfoBySessionToken(String sessionToken) {
        URI uri = UriBuilder.fromUri(leancloudConfig.getBaseUrl())
                .path("users")
                .path("me")
                .build();
        return client.target(uri)
                .request()
                .header("X-LC-Id", leancloudConfig.getAppId())
                .header("X-LC-Key", leancloudConfig.getAppKey())
                .header("X-LC-Session", sessionToken)
                .get(UserInfo.class);
    }
}
