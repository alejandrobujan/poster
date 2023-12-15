package es.udc.fi.dc.fd.rest.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.rest.dtos.PostStreamDto;

/**
 * The Class WebSocketControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class WebSocketControllerTest {

	/** The web socket controller */
	@Autowired
	WebSocketController webSocketController;

	/**
	 * Test notify new post
	 */
	@Test
	public void notifyNewPostTest() {
		PostStreamDto postStreamDto = new PostStreamDto("posts.newPost");

		assertEquals(postStreamDto, webSocketController.notifyNewPost(postStreamDto));
	}

}
