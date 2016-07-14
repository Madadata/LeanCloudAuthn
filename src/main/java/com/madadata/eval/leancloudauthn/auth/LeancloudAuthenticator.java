package com.madadata.eval.leancloudauthn.auth;


import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.madadata.eval.leancloudauthn.api.AppUser;
import com.madadata.eval.leancloudauthn.api.UserInfo;
import com.madadata.eval.leancloudauthn.config.LeancloudConfig;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudAuthenticator implements Authenticator<BasicCredentials, AppUser> {

    private static final Logger logger = getLogger(LeancloudAuthenticator.class);

    private final Client client;
    private final LeancloudConfig leancloudConfig;

    public LeancloudAuthenticator(Client client, LeancloudConfig leancloudConfig) {
        this.client = Objects.requireNonNull(client, "client");
        this.leancloudConfig = Objects.requireNonNull(leancloudConfig, "config");
    }

    @SuppressWarnings("Guava")
    @Override
    public Optional<AppUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
        String objectId = credentials.getUsername();
        String sessionToken = credentials.getPassword();
        if (Strings.isNullOrEmpty(objectId) || Strings.isNullOrEmpty(sessionToken)) {
            return Optional.absent();
        }
        URI uri = UriBuilder.fromUri(leancloudConfig.getBaseUrl())
                .path("users")
                .path("me")
                .build();
        try {
            UserInfo response = client.target(uri)
                    .request()
                    .header("X-LC-Id", leancloudConfig.getAppId())
                    .header("X-LC-Key", leancloudConfig.getAppKey())
                    .header("X-LC-Session", sessionToken)
                    .get(UserInfo.class);
            if (response != null && Objects.equals(objectId, response.getObjectId())) {
                return Optional.of(new AppUser(response));
            }
        } catch (BadRequestException e) {
            logger.debug("exception during query, it's most likely that the session token is invalid");
        } catch (Exception e) {
            logger.warn("this is really not supposed to happen", e);
            throw new AuthenticationException(e);
        }
        return Optional.absent();
    }
}
