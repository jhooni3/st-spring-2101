package com.example.demo.part.com.controller;


import ch.qos.logback.classic.Logger;
import com.example.demo.part.com.service.OneService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/one")
public class OneController {

    private static Logger log = (Logger) LoggerFactory.getLogger(OneController.class);

    @Autowired
    OneService service;

    public OneController(OneService service) {
        log.info("test logger");
        log.info("test debug logger");
        this.service = service;
    }

    @GetMapping
    public String one() {

        return service.one();
    }
}
