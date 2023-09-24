package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.User;
import jakarta.transaction.Transactional;

/**
 * The Class PostServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PostServiceTest {

	/** The user service. */
	@Autowired
	private PostService postService;

	/**
	 * Creates the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	private User createUser(String userName) {
		return new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");
	}
	
	/**
	 * Creates the category.
	 *
	 * @return the category
	 */
	private Category createCategory(String categoryName) {
		Category category = new Category();
		category.setName(categoryName);;
		return category;
	}
	
	/**
	 * Creates the post.
	 *
	 * @param title the title of the post
	 * @return the post
	 */
	private Post createPost(User user, Category category) {
		return new Post("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category);
	}

	/**
	 * Test find all categories.
	 *
	 */
	@Test
	public void testfindAllCategories() {
		Category category1 = createCategory("category1");
		Category category2 = createCategory("category2");
		Category category3 = createCategory("category3");
		List<Category> expectedListCategory = new ArrayList<>();
		expectedListCategory.add(category1);
		expectedListCategory.add(category2);
		expectedListCategory.add(category3);
		
		List<Category> listCategory = postService.findAllCategories();
		
		assertEquals(expectedListCategory, listCategory);
		
	}


}
