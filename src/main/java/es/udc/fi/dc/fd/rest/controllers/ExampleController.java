package es.udc.fi.dc.fd.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * The Class ExampleController.
 */
@RestController
public class ExampleController {
    
    /**
     * Hello.
     *
     * @return the string
     */
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }
}
