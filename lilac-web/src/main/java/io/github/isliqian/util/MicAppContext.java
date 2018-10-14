package io.github.isliqian.util;


import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 通过Class获取bean
 */
@Service
@Lazy(false)
public class MicAppContext implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;
    private static Logger logger = LoggerFactory.getLogger(MicAppContext.class);

    public MicAppContext() {
    }

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBeansOfType(requiredType);
    }

    public static void clearHolder() {
        if (logger.isDebugEnabled()) {
            logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        }

        applicationContext = null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        applicationContext = applicationContext;
    }

    public void destroy() throws Exception {
        clearHolder();
    }

    private static void assertContextInjected() {
        Validate.validState(applicationContext != null, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.", new Object[0]);
    }
}

