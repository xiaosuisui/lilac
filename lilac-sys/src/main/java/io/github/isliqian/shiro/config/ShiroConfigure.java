package io.github.isliqian.shiro.config;

import io.github.isliqian.shiro.filter.CasFilter;
import io.github.isliqian.shiro.filter.UserFilter;
import io.github.isliqian.shiro.filter.JwtFilter;
import io.github.isliqian.shiro.realm.JwtRealm;
import io.github.isliqian.shiro.realm.SystemAuthorizingRealm;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfigure {
    /**
     * 先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
     */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/signin");
        factoryBean.setSuccessUrl("/"); // 登录成功后跳转的路径
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        //设置我们自定义的JWT过滤器
        filterMap.put("user",new UserFilter());
        filterMap.put("cas",new CasFilter());
        filterMap.put("jwt", new JwtFilter());

        factoryBean.setFilters(filterMap);
        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        Map<String, String> filterRuleMap = new HashMap<>();
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/api/**", "jwt");
        filterRuleMap.put("/static/**", "anon"); // 静态资源匿名访问
        filterRuleMap.put("/layuiadmin/**", "anon"); // 静态资源匿名访问
        //开放登陆方法接口
        filterRuleMap.put("/login", "anon");
        //生成代码开放路径
        filterRuleMap.put("/result/mbg.zip", "anon");
        //开放接口文档url
        filterRuleMap.put("/swagger-ui.html","anon");
        // 访问 /unauthorized/** 不通过JWTFilter
        filterRuleMap.put("/unauthorized/**", "anon");
        filterRuleMap.put("/logout","logout");
        filterRuleMap.put("/**","authc");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return factoryBean;
    }
    //配置自定义的权限登录器
    @Bean
    public JwtRealm jwtRealm() {
        JwtRealm jwtRealm=new JwtRealm();
        return jwtRealm;
    }
    @Bean
    public SystemAuthorizingRealm systemAuthorizingRealm() {
        SystemAuthorizingRealm systemAuthorizingRealm=new SystemAuthorizingRealm();
        return systemAuthorizingRealm;
    }
    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realms=new ArrayList<>();
        realms.add(systemAuthorizingRealm());
        realms.add(jwtRealm());
        securityManager.setRealms(realms);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }
    /**
     * 系统自带的Realm管理，主要针对多realm
     * */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        ModularRealmAuthenticator modularRealmAuthenticator=new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }
    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
