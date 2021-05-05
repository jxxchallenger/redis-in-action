package io.jxxchallenger.spring.session.redis.sample;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import io.jxxchallenger.spring.session.redis.sample.web.config.SessionConfig;

public class SessionServletInitializer extends AbstractHttpSessionApplicationInitializer {

    public SessionServletInitializer() {
        super(SessionConfig.class);
    }

}
