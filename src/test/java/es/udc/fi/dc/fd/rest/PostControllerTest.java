package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class PostControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PostControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	
	/**
	 * Test get find all posts ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetFindAllPosts_Ok() throws Exception {

		mockMvc.perform(get("/api/posts/feed")).andExpect(status().isOk());

	}
	
	/**
	 * Test post find all categories ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetFindAllCategories_Ok() throws Exception {

		mockMvc.perform(get("/api/posts/categories")).andExpect(status().isOk());

	}
}
