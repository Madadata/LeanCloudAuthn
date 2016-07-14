package com.madadata.eval.leancloudauthn.resource;

import com.madadata.eval.leancloudauthn.api.UserInfo;
import com.madadata.eval.leancloudauthn.client.LeancloudClient;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

/**
 * Created by jiayu on 7/14/16.
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class LeancloudAuthenticationService {

    private final LeancloudClient leancloudClient;

    public LeancloudAuthenticationService(LeancloudClient leancloudClient) {
        this.leancloudClient = Objects.requireNonNull(leancloudClient);
    }

    @RolesAllowed(value = {"Secret:Read"})
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/secret")
    public String protectedResource() {
        return "this is a secret";
    }

    /**
     * TODO: session token is embedded in URL, and also sent out in header, is this safe?
     */
    @GET
    @Path("/validate/{sessionToken}")
    public UserInfo validateSessionKey(@NotEmpty @PathParam("sessionToken") String sessionToken) {
        return leancloudClient.getUserInfoBySessionToken(sessionToken);
    }

    @GET
    @Path("/user/{userId}")
    public UserInfo getUser(@NotEmpty @PathParam("userId") String userId) {
        return leancloudClient.getUserInfoById(userId);
    }

}
