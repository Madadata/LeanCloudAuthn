package com.madadata.eval.leancloudauthn.auth;

import com.madadata.eval.leancloudauthn.api.AppUser;
import io.dropwizard.auth.Authorizer;

/**
 * Created by jiayu on 7/14/16.
 */
public class LeancloudAuthorizor implements Authorizer<AppUser> {

    @Override
    public boolean authorize(AppUser principal, String role) {
        return true;
    }

}
