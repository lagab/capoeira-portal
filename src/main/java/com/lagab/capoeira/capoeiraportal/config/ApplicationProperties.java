package com.lagab.capoeira.capoeiraportal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@ConfigurationProperties(
        prefix = "application",
        ignoreUnknownFields = true
)
public class ApplicationProperties {

    private final ApplicationProperties.Mail mail = new ApplicationProperties.Mail();
    private final ApplicationProperties.Security security = new ApplicationProperties.Security();

    public ApplicationProperties(){

    }

    public Mail getMail() {
        return mail;
    }
    public ApplicationProperties.Security getSecurity() {
        return this.security;
    }

    public static class Mail {
        private boolean enabled = false;
        private String from = "";
        private String baseUrl = "";

        public Mail() {
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getFrom() {
            return this.from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getBaseUrl() {
            return this.baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    public static class Security {
        private final ApplicationProperties.Security.Authentication authentication = new ApplicationProperties.Security.Authentication();

        public Security() {
        }


        public ApplicationProperties.Security.Authentication getAuthentication() {
            return this.authentication;
        }

        public static class Authentication {
            private final ApplicationProperties.Security.Authentication.Jwt jwt = new ApplicationProperties.Security.Authentication.Jwt();

            public Authentication() {
            }

            public ApplicationProperties.Security.Authentication.Jwt getJwt() {
                return this.jwt;
            }

            public static class Jwt {
                private String secret;
                private String base64Secret;
                private long tokenValidityInSeconds;
                private long tokenValidityInSecondsForRememberMe;

                public Jwt() {
                    /*this.secret = ApplicationProperties.Security.Authentication.Jwt.secret;
                    this.base64Secret = ApplicationProperties.Security.Authentication.Jwt.base64Secret;
                    this.tokenValidityInSeconds = 1800L;
                    this.tokenValidityInSecondsForRememberMe = 2592000L;*/
                }

                public String getSecret() {
                    return this.secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public String getBase64Secret() {
                    return this.base64Secret;
                }

                public void setBase64Secret(String base64Secret) {
                    this.base64Secret = base64Secret;
                }

                public long getTokenValidityInSeconds() {
                    return this.tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return this.tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }

    }
}