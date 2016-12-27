package com.tindfire.model.AuthModel;

/**
 * Created by vcareall on 27/12/16.
 */
public class AuthRequest {
    String facebook_token;
    String facebook_id;


    private AuthRequest(Builder builder) {
        facebook_token = builder.facebook_token;
        facebook_id = builder.facebook_id;
    }

    public static final class Builder {
        private String facebook_token;
        private String facebook_id;

        public Builder() {

        }

        public Builder facebookToken(String val) {
            facebook_token = val;
            return this;
        }

        public Builder facebookId(String val) {
            facebook_id = val;
            return this;
        }

        public AuthRequest build() {
            return new AuthRequest(this);
        }
    }


}
