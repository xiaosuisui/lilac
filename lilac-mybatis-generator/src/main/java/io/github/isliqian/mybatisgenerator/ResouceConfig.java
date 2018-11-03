package io.github.isliqian.mybatisgenerator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lis on 17/5/15.
 */
@Configuration
public class ResouceConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = System.getProperty("user.dir");
        System.out.println(path);
        registry.addResourceHandler("/result/**").addResourceLocations("file:" + path + "/result/");
        super.addResourceHandlers(registry);
    }
}
