package io.github.isliqian.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWT TOKEN 验证
 */
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
