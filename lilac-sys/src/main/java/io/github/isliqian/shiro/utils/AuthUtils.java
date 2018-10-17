package io.github.isliqian.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.security.Principal;

public class AuthUtils {
    public AuthUtils() {
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException var2) {
            ;
        } catch (InvalidSessionException var3) {
            ;
        }

        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }

            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException var2) {
            ;
        }

        return null;
    }

    public static Object getCache(String key) {
        return getCache(key, (Object)null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
        getSession().removeAttribute(key);
    }
}
