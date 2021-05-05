package io.jxxchallenger.spring.session.redis.sample.web.config.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping(value = {"/"})
public class IndexController {

    @GetMapping
    public Map<?, ?> index() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("hello", "spring session");
        return result;
    }
    
    @PostMapping("/session")
    public Map<?, ?> createSession(HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        
        HttpSession session = request.getSession();
        result.put("isNew", session.isNew()? "true":"fase");
        result.put("id", session.getId());
        result.put("maxInactiveInterval", String.valueOf(session.getMaxInactiveInterval()));
        session.setAttribute("sessionAttrSample", new SessionAttrSample("spring session"));
        return result;
        
    }
    
    @PostMapping("/edit")
    public Map<?, ?> edit(@SessionAttribute(name = "sessionAttrSample") SessionAttrSample example) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("sessionAttrSample", example);
        return result;
    }
}
