package es.udc.fi.dc.fd.rest.controllers;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import es.udc.fi.dc.fd.rest.dtos.PostStreamDto;

/**
 * The Class WebSocketController.
 */
@Controller
public class WebSocketController {

	/**
	 * Notify new post
	 * 
	 * @param message the message
	 * @return the post stream dto
	 */
	@SendTo("/topic/posts")
	public PostStreamDto notifyNewPost(PostStreamDto message) {
		return message;
	}
}
