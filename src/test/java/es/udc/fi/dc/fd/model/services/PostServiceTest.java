package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fi.dc.fd.model.entities.*;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.User;
import jakarta.transaction.Transactional;
import java.sql.*;

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
	
    @Autowired
    private CategoryDao categoryDao;

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
	private Category createCategory(long id, String categoryName) {
		Category category = new Category();
		category.setId(id);
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
		Category c1 = createCategory(1,"Comida");
		Category c2 = createCategory(2,"Motor");
		Category c3 = createCategory(3,"Hogar");
		Category c4 = createCategory(4,"Juguetes");
		Category c5 = createCategory(5,"Tecnologia");
		Category c6 = createCategory(6,"Entretenimiento");
		List<Category> expectedListCategory = new ArrayList<>();
		Collections.addAll(expectedListCategory, c1, c2, c3, c4, c5, c6);

		
		List<Category> listCategory = postService.findAllCategories();
		
		assertEquals(expectedListCategory.size(), listCategory.size());
		
		for (int i = 0; i < listCategory.size(); i++) {
			assertEquals(listCategory.get(i).getId(), expectedListCategory.get(i).getId());
			assertEquals(listCategory.get(i).getName(), expectedListCategory.get(i).getName());
		}
		
	}

    @Test
    public void testFindNoCategories() {
    	
    	categoryDao.deleteAll();
        List<Category> emptyList = new ArrayList<>();
        assertEquals(emptyList, postService.findAllCategories());
    	
    }

}
