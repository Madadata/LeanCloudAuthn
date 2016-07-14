package com.madadata.eval.leancloudauthn.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * Created by jiayu on 7/14/16.
 */
public class UserInfo {

    @NotEmpty
    private final String objectId;
    private final String phone;
    @NotEmpty
    private final String username;
    private final String sessionToken;
    private final DateTime updatedAt;
    private final DateTime createdAt;
    private final boolean emailVerified;
    private final boolean mobilePhoneVerified;

    @JsonCreator
    public UserInfo(@JsonProperty("objectId") String objectId,
                    @JsonProperty("phone") String phone,
                    @JsonProperty("username") String username,
                    @JsonProperty("sessionToken") String sessionToken,
                    @JsonProperty("updatedAt") DateTime updatedAt,
                    @JsonProperty("createdAt") DateTime createdAt,
                    @JsonProperty("emailVerified") boolean emailVerified,
                    @JsonProperty("mobilePhoneVerified") boolean mobilePhoneVerified) {
        this.objectId = objectId;
        this.phone = phone;
        this.username = username;
        this.sessionToken = sessionToken;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.emailVerified = emailVerified;
        this.mobilePhoneVerified = mobilePhoneVerified;
    }

    @JsonProperty
    public String getObjectId() {
        return objectId;
    }

    @JsonProperty
    public String getPhone() {
        return phone;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public String getSessionToken() {
        return sessionToken;
    }

    @JsonProperty
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty
    public DateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty
    public boolean isEmailVerified() {
        return emailVerified;
    }

    @JsonProperty
    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }
}
