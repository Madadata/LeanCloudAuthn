package com.madadata.eval.leancloudauthn.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

/**
 * Created by jiayu on 7/14/16.
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class LeancloudAuthenticationService {

    private final Client client;

    public LeancloudAuthenticationService(Client client) {
        this.client = Objects.requireNonNull(client, "client");
    }


}
