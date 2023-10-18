package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.rest.dtos.SearchFiltersDto;
import es.udc.fi.dc.fd.rest.dtos.SearchParamsDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CatalogControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The Constant PASSWORD. */
	private final static String PASSWORD = "password";

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private CategoryDao categoryDao;

	private Offer createOffer(User user) {
		return postDao.save(new Offer("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user,
				createCategory("Hola")));
	}

	private Category createCategory(String name) {
		return categoryDao.save(new Category(name));
	}

	private User createUser(String userName) {

		User user = new User(userName, PASSWORD, "newUser", "user", "user@test.com", null);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.USER);
		return userDao.save(user);
	}

	/**
	 * Test get find all posts ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostFindAllPosts_Ok() throws Exception {
		SearchParamsDto searchParamsDto = new SearchParamsDto(new SearchFiltersDto(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false), "", 0);
		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/catalog/feed").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(searchParamsDto))).andExpect(status().isOk());
	}

	/**
	 * Test post find all categories ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetFindAllCategories_Ok() throws Exception {

		mockMvc.perform(get("/api/catalog/categories")).andExpect(status().isOk());

	}

	@Test
	public void testGetFindPostById_Ok() throws Exception {

		User user = createUser("admin");

		Offer offer = createOffer(user);

		mockMvc.perform(get("/api/catalog/postDetail/" + offer.getId())).andExpect(status().isOk());

	}

	@Test
	public void testGetFindPostById_NotOk() throws Exception {
		mockMvc.perform(get("/api/catalog/postDetail/-10")).andExpect(status().isNotFound());

	}
}
