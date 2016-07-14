package com.madadata.eval.leancloudauthn.auth;


import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.madadata.eval.leancloudauthn.api.AppUser;
import com.madadata.eval.leancloudauthn.api.UserInfo;
import com.madadata.eval.leancloudauthn.client.LeancloudClient;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;

import javax.ws.rs.BadRequestException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudAuthenticator implements Authenticator<BasicCredentials, AppUser> {

    private static final Logger logger = getLogger(LeancloudAuthenticator.class);

    private final LeancloudClient leancloudClient;

    public LeancloudAuthenticator(LeancloudClient leancloudClient) {
        this.leancloudClient = leancloudClient;
    }

    @SuppressWarnings("Guava")
    @Override
    public Optional<AppUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
        String objectId = credentials.getUsername();
        String sessionToken = credentials.getPassword();
        if (Strings.isNullOrEmpty(objectId) || Strings.isNullOrEmpty(sessionToken)) {
            return Optional.absent();
        }
        try {
            UserInfo response = leancloudClient.getUserInfoBySessionToken(sessionToken);
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
