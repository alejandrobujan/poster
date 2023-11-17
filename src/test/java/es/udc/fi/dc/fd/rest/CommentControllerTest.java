/**
 * 
 */
package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.udc.fi.dc.fd.model.common.exceptions.DuplicateInstanceException;
import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.CommentDao;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.UserService;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.rest.common.JwtGenerator;
import es.udc.fi.dc.fd.rest.common.JwtInfo;
import es.udc.fi.dc.fd.rest.controllers.UserController;
import es.udc.fi.dc.fd.rest.dtos.AuthenticatedUserDto;
import es.udc.fi.dc.fd.rest.dtos.CommentParamsDto;
import es.udc.fi.dc.fd.rest.dtos.FindCommentsParamsDto;
import es.udc.fi.dc.fd.rest.dtos.LoginParamsDto;

/**
 * The class CommentControllerTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CommentControllerTest {

	/** The authenticated user dto */
	private AuthenticatedUserDto authenticatedUser;

	/** The offer */
	private Post offer;

	/** The category */
	private Category category;

	/** The parent comment */
	private Comment parentComment;

	/** The parent comment response */
	private Comment parentCommentResponse;

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The comment dao. */
	@Autowired
	private CommentDao commentDao;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private UserService userService;

	/** The user controller. */
	@Autowired
	private UserController userController;

	/** The jwt generator. */
	@Autowired
	private JwtGenerator jwtGenerator;

	/** The Constant NON_EXISTENT_ID. */
	private final static Long NON_EXISTENT_ID = -1L;

	/** The Constant PASSWORD. */
	private final static String PASSWORD = "password";

	/**
	 * Creates the authenticated user.
	 * 
	 * @param userName the user name
	 * @param roleType the role type
	 * @return the authenticated user dto
	 * @throws IncorrectLoginException the incorrect login exception
	 */
	private AuthenticatedUserDto createAuthenticatedUser(String userName, RoleType roleType)
			throws IncorrectLoginException {
		User user = new User(userName, PASSWORD, "newUser", "user", "user@test.com", null);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(roleType);

		userDao.save(user);

		LoginParamsDto loginParams = new LoginParamsDto();
		loginParams.setUserName(user.getUserName());
		loginParams.setPassword(PASSWORD);

		return userController.login(loginParams);
	}

	/**
	 * Creates the offer.
	 *
	 * @param title    the title of the post
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the offer
	 */
	private Post createOffer(String title, User user, Category category) {
		return postDao.save(new Offer(title, "description", "url", new BigDecimal(10), LocalDateTime.now(), user,
				category, LocalDateTime.now()));
	}

	/**
	 * Creates the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	private User createUser(String userName) {
		User user = new User(userName, PASSWORD, "newUser", "user", "user@test.com", null);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.USER);

		return userDao.save(user);
	}

	/**
	 * Creates the category.
	 *
	 * @param name the name of the category
	 * @return the category
	 */
	private Category createCategory(String name) {
		return categoryDao.save(new Category(name));
	}

	/**
	 *
	 * Generate service token.
	 * 
	 * @param user the user
	 * @return the string
	 */
	private String generateServiceToken(User user) {

		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getUserName(), user.getRole().toString());

		return jwtGenerator.generate(jwtInfo);

	}

	/**
	 * Creates the offer.
	 * 
	 * @param parent the parent of the comment
	 * @param post   the post of the comment
	 * @param user   the user of the comment
	 * @return the Comment
	 */
	private Comment createComment(User user, Post post, Comment parent) {
		return commentDao.save(new Comment("description", LocalDateTime.now(), user, post, parent, 1, 0));
	}

	/**
	 * Creates the offer.
	 *
	 * @param post the post of the comment
	 * @param user the user of the comment
	 * @return the Comment
	 */
	private Comment createComment(User user, Post post) {
		return commentDao.save(new Comment("description", LocalDateTime.now(), user, post, null, 1, 0));
	}

	/**
	 * Set up
	 * 
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws IncorrectLoginException           the incorrect login exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException, IncorrectLoginException,
			InstanceNotFoundException {
		authenticatedUser = createAuthenticatedUser("user", RoleType.USER);
		category = createCategory("category");
		offer = createOffer("offer", userService.loginFromId(authenticatedUser.getUserDto().getId()), category);
		parentComment = createComment(createUser("Pepe"), offer);
		parentCommentResponse = createComment(createUser("Pepe"), offer, parentComment);
	}

	@Test
	public void testCreateComment_Ok() throws Exception {
		CommentParamsDto commentParamsDto = new CommentParamsDto();
		commentParamsDto.setCommentParentId(null);
		commentParamsDto.setDescription("Hola");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + offer.getId() + "/comment")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(commentParamsDto)))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreateComment_NoPost() throws Exception {
		CommentParamsDto commentParamsDto = new CommentParamsDto();
		commentParamsDto.setCommentParentId(null);
		commentParamsDto.setDescription("Hola");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + NON_EXISTENT_ID + "/comment")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(commentParamsDto)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testCreateComment_NoUser() throws Exception {
		CommentParamsDto commentParamsDto = new CommentParamsDto();
		commentParamsDto.setCommentParentId(null);
		commentParamsDto.setDescription("Hola");

		User nobody = createUser("nobody");
		nobody.setId(NON_EXISTENT_ID);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + offer.getId() + "/comment")
				.header("Authorization", "Bearer " + generateServiceToken(nobody))
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(commentParamsDto)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testCreateAnswer_Ok() throws Exception {
		CommentParamsDto commentParamsDto = new CommentParamsDto();
		commentParamsDto.setCommentParentId(parentComment.getId());
		commentParamsDto.setDescription("Hola");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + offer.getId() + "/comment")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(commentParamsDto)))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreateAnswer_NoPost() throws Exception {
		CommentParamsDto commentParamsDto = new CommentParamsDto();
		commentParamsDto.setCommentParentId(parentComment.getId());
		commentParamsDto.setDescription("Hola");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + NON_EXISTENT_ID + "/comment")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(commentParamsDto)))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreateAnswer_NoUser() throws Exception {
		CommentParamsDto commentParamsDto = new CommentParamsDto();
		commentParamsDto.setCommentParentId(parentComment.getId());
		commentParamsDto.setDescription("Hola");

		User nobody = createUser("nobody");
		nobody.setId(NON_EXISTENT_ID);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + offer.getId() + "/comment")
				.header("Authorization", "Bearer " + generateServiceToken(nobody))
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(commentParamsDto)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testFindComments_Ok() throws JsonProcessingException, Exception {
		FindCommentsParamsDto findCommentsParamsDto = new FindCommentsParamsDto(0, null);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + offer.getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(findCommentsParamsDto)))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindComments_OkEmpty() throws JsonProcessingException, Exception {
		FindCommentsParamsDto findCommentsParamsDto = new FindCommentsParamsDto(0, null);

		Post newOffer = createOffer("Oferta", createUser("Pepe2"), createCategory("Motos"));

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + newOffer.getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(findCommentsParamsDto)))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindAnswers_Ok() throws JsonProcessingException, Exception {
		FindCommentsParamsDto findCommentsParamsDto = new FindCommentsParamsDto(0, parentComment.getId());

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + offer.getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(findCommentsParamsDto)))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindAnswers_OkEmpty() throws JsonProcessingException, Exception {
		FindCommentsParamsDto findCommentsParamsDto = new FindCommentsParamsDto(0, parentCommentResponse.getId());

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/comments/post/" + offer.getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(findCommentsParamsDto)))
				.andExpect(status().isOk());
	}

}
