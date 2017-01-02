package com.tindfire.model.GetMatchModel;

/**
 * Created by vcareall on 30/12/16.
 */

public class AuthMatch {
    String facebook_id;
    private AuthMatch(AuthMatch.Builder builder) {
        facebook_id = builder.facebook_id;
    }

    public static final class Builder {
        private String facebook_id;

        public Builder() {

        }

        public AuthMatch.Builder facebookId(String val) {
            facebook_id = val;
            return this;
        }

        public AuthMatch build() {
            return new AuthMatch(this);
        }
    }

}
