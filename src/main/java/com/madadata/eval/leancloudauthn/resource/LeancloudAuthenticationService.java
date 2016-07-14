package com.madadata.eval.leancloudauthn.resource;

import com.madadata.eval.leancloudauthn.api.UserInfo;
import com.madadata.eval.leancloudauthn.config.LeancloudConfig;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Created by jiayu on 7/14/16.
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class LeancloudAuthenticationService {

    private final Client client;
    private final LeancloudConfig leancloudConfig;


    public LeancloudAuthenticationService(Client client, LeancloudConfig leancloudConfig) {
        this.client = requireNonNull(client, "client");
        this.leancloudConfig = requireNonNull(leancloudConfig, "leancloud config");
    }

    @Path("/validate/{sessionToken}")
    public UserInfo validateSessionKey(@NotEmpty @PathParam("sessionToken") String sessionToken) {
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

    @Path("/user/{userId}")
    public UserInfo getUser(@NotEmpty @PathParam("userId") String userId) {
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

}
