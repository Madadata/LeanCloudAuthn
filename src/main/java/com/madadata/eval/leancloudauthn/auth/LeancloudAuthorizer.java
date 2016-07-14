package com.madadata.eval.leancloudauthn.auth;

import com.madadata.eval.leancloudauthn.api.AppUser;
import com.madadata.eval.leancloudauthn.config.LeancloudConfig;
import io.dropwizard.auth.Authorizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudAuthorizer implements Authorizer<AppUser> {

    private final Client client;
    private final LeancloudConfig leancloudConfig;

    public LeancloudAuthorizer(Client client, LeancloudConfig leancloudConfig) {
        this.client = client;
        this.leancloudConfig = leancloudConfig;
    }

    private static final Logger logger = LoggerFactory.getLogger(LeancloudAuthorizer.class);

    @Override
    public boolean authorize(AppUser principal, String role) {
        logger.info("authorizing {} with role '{}'",
                principal.getUserInfo(), role);
        // note that this role is resource-specific and can be different
        // from the user-role architecture
        return true;
    }

}
