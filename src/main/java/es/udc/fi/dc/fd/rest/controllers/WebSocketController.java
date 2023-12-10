package es.udc.fi.dc.fd.rest.controllers;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import es.udc.fi.dc.fd.rest.dtos.PostStreamDto;

@Controller
public class WebSocketController {

    @SendTo("/topic/posts")
    public PostStreamDto notifyNewPost(PostStreamDto message) {
        return message; 
    }
}
