package es.udc.fi.dc.fd.rest.dtos;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostStreamDtoTest {

    @Test
    public void testConstructorNotNull() {
        String data = "posts.newPost";

        PostStreamDto postStreamDto = new PostStreamDto(data);

        assertNotNull(postStreamDto);
    }

    @Test
    public void testDataGetter() {
        String data = "posts.newPost";

        PostStreamDto postStreamDto = new PostStreamDto(data);

        assertEquals(data, postStreamDto.getData());
    }
}
