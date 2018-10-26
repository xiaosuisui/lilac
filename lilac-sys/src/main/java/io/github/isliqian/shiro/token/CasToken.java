package io.github.isliqian.shiro.token;


import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class CasToken implements RememberMeAuthenticationToken {
    private static final long serialVersionUID = 8587329689973009598L;
    private String ticket = null;
    private String userId = null;
    private boolean isRememberMe = false;

    public CasToken(String ticket) {
        this.ticket = ticket;
    }

    public Object getPrincipal() {
        return this.userId;
    }

    public Object getCredentials() {
        return this.ticket;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isRememberMe() {
        return this.isRememberMe;
    }

    public void setRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }
}
