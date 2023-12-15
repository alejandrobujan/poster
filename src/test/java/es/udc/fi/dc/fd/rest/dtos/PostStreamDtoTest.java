package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

/**
 * The class PostStreamDtoTest
 */
public class PostStreamDtoTest {

	/** Test constructor not null */
	@Test
	public void testConstructorNotNull() {
		String data = "posts.newPost";

		PostStreamDto postStreamDto = new PostStreamDto(data);

		assertNotNull(postStreamDto);
	}

	/** Test data getter */
	@Test
	public void testDataGetter() {
		String data = "posts.newPost";

		PostStreamDto postStreamDto = new PostStreamDto(data);

		assertEquals(data, postStreamDto.getData());
	}
}
