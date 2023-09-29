package es.udc.fi.dc.fd.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class PostTest {
	@Test
	public void testAddImage() {
		Post post = new Post();
		Image image = new Image();

		post.addImage(image);

		assertTrue(post.getImages().contains(image));
		assertEquals(image.getPost(), post);
	}
}