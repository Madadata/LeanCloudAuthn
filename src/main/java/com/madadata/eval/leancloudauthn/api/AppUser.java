package com.madadata.eval.leancloudauthn.api;

import java.security.Principal;
import java.util.Objects;

/**
 * Created by jiayu on 7/14/16.
 */
public class AppUser implements Principal {

    private final UserInfo userInfo;

    public AppUser(UserInfo userInfo) {
        this.userInfo = Objects.requireNonNull(userInfo, "user info");
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public String getName() {
        return userInfo.getObjectId();
    }
}
