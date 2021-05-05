package io.jxxchallenger.spring.session.redis.sample.web.config.controller;

import java.io.Serializable;

public class SessionAttrSample implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 6470779748510501151L;
    String name;

    public SessionAttrSample() {
        super();
    }

    public SessionAttrSample(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
