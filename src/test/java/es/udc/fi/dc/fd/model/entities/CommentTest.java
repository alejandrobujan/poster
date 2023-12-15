package es.udc.fi.dc.fd.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * The Class CommentTest.
 */
public class CommentTest {

	/**
	 * Test setters.
	 */
	@Test
	public void testSetters() {
		Comment comment = new Comment();

		comment.setDescription("New Description");
		comment.setDate(LocalDateTime.now());
		comment.setUser(new User());
		comment.setPost(new Post());
		comment.setComment(new Comment());
		comment.setLevel(2);
		comment.setAnswers(3);

		assertEquals("New Description", comment.getDescription());
		assertNotNull(comment.getDate());
		assertNotNull(comment.getUser());
		assertNotNull(comment.getPost());
		assertNotNull(comment.getComment());
		assertEquals(2, comment.getLevel());
		assertEquals(3, comment.getAnswers());
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() {
		Comment comment = new Comment("Description", LocalDateTime.now(), new User(), new Post(), null, 1, 0);

		String expectedToString = "Comment [id=null, description=Description, date=" + comment.getDate() + ", user="
				+ comment.getUser() + ", post=" + comment.getPost() + ", comment=null, level=1, answers=0]";

		assertEquals(expectedToString, comment.toString());
	}
}
