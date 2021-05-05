package io.jxxchallenger.spring.session.redis.sample;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import io.jxxchallenger.spring.session.redis.sample.web.config.WebConfig;

public class DemoServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        
        return new String[] {"/"};
    }

}
