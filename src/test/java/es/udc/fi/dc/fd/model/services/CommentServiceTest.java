package es.udc.fi.dc.fd.model.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fi.dc.fd.model.common.exceptions.DuplicateInstanceException;
import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.CommentDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.InvalidCommentParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import jakarta.transaction.Transactional;

/**
 * The Class CommentServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CommentServiceTest {

	/** The non existent id */
	private final static long NON_EXISTENT_ID = -1L;

	/** The user */
	private User user;

	/** The category */
	private Category category;

	/** The post */
	private Post post;

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentDao commentDao;

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	/**
	 * Creates the post.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createPost(User user, Category category) {
		return postDao.save(new Post("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user,
				category, LocalDateTime.of(2025, 11, 11, 11, 11)));
	}

	/**
	 * Creates the category.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Category createCategory(String name) {
		return categoryDao.save(new Category(name));
	}

	/**
	 * Sign up user
	 * 
	 * @param userName the user name
	 * @return the registered user
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 */
	private User signUpUser(String userName) throws DuplicateInstanceException, MaximumImageSizeExceededException {

		byte avatar[] = new byte[] { 50 };
		User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", avatar);

		userService.signUp(user);

		return user;

	}

	/**
	 * The set up
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 */
	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		user = signUpUser("user");
		category = createCategory("category");
		post = createPost(user, category);
	}

	@Test
	public void testCreateCommentPost() throws InstanceNotFoundException, InvalidCommentParameterException {
		String description = "Pepe";
		int answers = 0;
		Comment parent = null;
		int level = 1;

		commentService.createComment(description, user.getId(), post.getId());
		List<Comment> comments = commentDao.findAll();

		assertEquals(1, comments.size());

		Comment commentFound = comments.get(0);

		assertEquals(description, commentFound.getDescription());
		assertEquals(user.getId(), commentFound.getUser().getId());
		assertEquals(post.getId(), commentFound.getPost().getId());
		assertEquals(answers, commentFound.getAnswers());
		assertEquals(parent, commentFound.getComment());
		assertEquals(level, commentFound.getLevel());
	}

	@Test
	public void testCreateCommentPostNullPost() throws InstanceNotFoundException, InvalidCommentParameterException {
		String description = "Pepe";

		assertThrows(InvalidCommentParameterException.class,
				() -> commentService.createComment(description, user.getId(), null));
	}

	@Test
	public void testCreateCommentPostInexistentPostId()
			throws InstanceNotFoundException, InvalidCommentParameterException {
		String description = "Pepe";

		assertThrows(InstanceNotFoundException.class,
				() -> commentService.createComment(description, user.getId(), NON_EXISTENT_ID));
	}

	@Test
	public void testCreateCommentPostInexistentUserId()
			throws InstanceNotFoundException, InvalidCommentParameterException {
		String description = "Pepe";

		assertThrows(InstanceNotFoundException.class,
				() -> commentService.createComment(description, NON_EXISTENT_ID, post.getId()));
	}

	@Test
	public void testAnswerComment() throws InstanceNotFoundException, InvalidCommentParameterException {
		String parentDescription = "Pepe";
		int parentAnswers = 1;

		String descriptionExpected = "Mario";
		int answersExpected = 0;
		int levelExpected = 2;

		commentService.createComment(parentDescription, user.getId(), post.getId());

		List<Comment> comments = commentDao.findAll();

		assertEquals(1, comments.size());

		Comment parent = comments.get(0);

		commentService.answerComment(descriptionExpected, user.getId(), parent.getId());

		Slice<Comment> answers = commentDao.findByPostIdAndCommentIdOrderByDateDesc(post.getId(), parent.getId(),
				PageRequest.of(0, 1));

		Comment answerFound = answers.getContent().get(0);

		assertEquals(1, answers.getContent().size());

		assertEquals(parentAnswers, parent.getAnswers());

		assertEquals(descriptionExpected, answerFound.getDescription());
		assertEquals(user.getId(), answerFound.getUser().getId());
		assertEquals(post.getId(), answerFound.getPost().getId());
		assertEquals(answersExpected, answerFound.getAnswers());
		assertEquals(parent, answerFound.getComment());
		assertEquals(levelExpected, answerFound.getLevel());
	}

	@Test
	public void testAnswerCommentPostNullPost() throws InstanceNotFoundException, InvalidCommentParameterException {
		String description = "Pepe";

		assertThrows(InvalidCommentParameterException.class,
				() -> commentService.answerComment(description, user.getId(), null));
	}

	@Test
	public void testAnswerCommentPostInexistentPostId()
			throws InstanceNotFoundException, InvalidCommentParameterException {
		String description = "Pepe";

		assertThrows(InstanceNotFoundException.class,
				() -> commentService.answerComment(description, user.getId(), NON_EXISTENT_ID));
	}

	@Test
	public void testAnswerCommentPostInexistentUserId()
			throws InstanceNotFoundException, InvalidCommentParameterException {
		String description = "Pepe";

		assertThrows(InstanceNotFoundException.class,
				() -> commentService.answerComment(description, NON_EXISTENT_ID, post.getId()));
	}

	@Test
	public void testFindComments() throws InstanceNotFoundException, InvalidCommentParameterException {
		String description = "Pepe";
		int answers = 0;
		int level = 1;

		Comment parent = null;
		int page = 0;
		int size = 6;

		commentService.createComment(description, user.getId(), post.getId());

		Block<Comment> commentsBlock = commentService.findComments(post.getId(), page, null, size);
		List<Comment> comments = commentsBlock.getItems();
		Comment commentFound = comments.get(0);

		assertFalse(commentsBlock.getExistMoreItems());

		assertEquals(1, comments.size());

		assertEquals(description, commentFound.getDescription());
		assertEquals(user.getId(), commentFound.getUser().getId());
		assertEquals(post.getId(), commentFound.getPost().getId());
		assertEquals(answers, commentFound.getAnswers());
		assertEquals(parent, commentFound.getComment());
		assertEquals(level, commentFound.getLevel());

	}

	@Test
	public void testFindAnswers() throws InstanceNotFoundException, InvalidCommentParameterException {
		String parentDescription = "Pepe";

		String description = "Mario";
		int answers = 0;
		int level = 2;
		int page = 0;
		int size = 6;

		commentService.createComment(parentDescription, user.getId(), post.getId());

		List<Comment> allComments = commentDao.findAll();
		Comment parent = allComments.get(0);

		commentService.answerComment(description, user.getId(), parent.getId());

		Block<Comment> commentsBlock = commentService.findComments(post.getId(), page, parent.getId(), size);
		List<Comment> comments = commentsBlock.getItems();
		Comment commentFound = comments.get(0);

		assertFalse(commentsBlock.getExistMoreItems());

		assertEquals(1, comments.size());

		assertEquals(description, commentFound.getDescription());
		assertEquals(user.getId(), commentFound.getUser().getId());
		assertEquals(post.getId(), commentFound.getPost().getId());
		assertEquals(answers, commentFound.getAnswers());
		assertEquals(parent, commentFound.getComment());
		assertEquals(level, commentFound.getLevel());

	}

	@Test
	public void testFindCommentsNoComments() throws InstanceNotFoundException, InvalidCommentParameterException {
		int page = 0;
		int size = 6;

		assertTrue(commentService.findComments(post.getId(), page, null, size).getItems().isEmpty());

	}

	@Test
	public void testFindCommentsNoPost() throws InstanceNotFoundException, InvalidCommentParameterException {
		int page = 0;
		int size = 6;

		assertTrue(commentService.findComments(NON_EXISTENT_ID, page, null, size).getItems().isEmpty());

	}

}
