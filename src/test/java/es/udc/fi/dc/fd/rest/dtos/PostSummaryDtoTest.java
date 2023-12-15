package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The class PostSummaryDtoTest
 */
public class PostSummaryDtoTest {

	/** The post summary dto */
	private PostSummaryDto post;

	/**
	 * Set up
	 */
	@BeforeEach
	public void setUp() {
		post = new PostSummaryDto();
	}

	/**
	 * Test default constructor
	 */
	@Test
	public void testDefaultConstructor() {
		assertNotNull(post);
	}

	/**
	 * Test parametrized constructor
	 */
	@Test
	public void testParametrizedConstructor() {
		post = new PostSummaryDto(1L, "Title", "Description", "URL", BigDecimal.TEN, new CategoryDto(),
				new ArrayList<>(), "Type");
		assertNotNull(post);
	}

	/**
	 * Test get id
	 */
	@Test
	public void testGetId() {
		Long id = 1L;
		post = new PostSummaryDto(id, "Title", "Description", "URL", BigDecimal.TEN, new CategoryDto(),
				new ArrayList<>(), "Type");
		assertEquals(id, post.getId());
	}

	/**
	 * Test get title
	 */
	@Test
	public void testGetTitle() {
		String title = "Title";
		post = new PostSummaryDto(1L, title, "Description", "URL", BigDecimal.TEN, new CategoryDto(), new ArrayList<>(),
				"Type");
		assertEquals(title, post.getTitle());
	}

	/**
	 * Test get description
	 */
	@Test
	public void testGetDescription() {
		String description = "Description";
		post = new PostSummaryDto(1L, "Title", description, "URL", BigDecimal.TEN, new CategoryDto(), new ArrayList<>(),
				"Type");
		assertEquals(description, post.getDescription());
	}

	/**
	 * Test get url
	 */
	@Test
	public void testGetUrl() {
		String url = "URL";
		post = new PostSummaryDto(1L, "Title", "Description", url, BigDecimal.TEN, new CategoryDto(), new ArrayList<>(),
				"Type");
		assertEquals(url, post.getUrl());
	}

	/**
	 * Test get price
	 */
	@Test
	public void testGetPrice() {
		BigDecimal price = BigDecimal.TEN;
		post = new PostSummaryDto(1L, "Title", "Description", "URL", price, new CategoryDto(), new ArrayList<>(),
				"Type");
		assertEquals(price, post.getPrice());
	}

	/**
	 * Test get category dto
	 */
	@Test
	public void testGetCategoryDto() {
		CategoryDto categoryDto = new CategoryDto();
		post = new PostSummaryDto(1L, "Title", "Description", "URL", BigDecimal.TEN, categoryDto, new ArrayList<>(),
				"Type");
		assertEquals(categoryDto, post.getCategoryDto());
	}

	/**
	 * Test get images
	 */
	@Test
	public void testGetImages() {
		List<byte[]> images = new ArrayList<>();
		images.add(new byte[] { 1, 2, 3 });
		images.add(new byte[] { 4, 5, 6 });
		post = new PostSummaryDto(1L, "Title", "Description", "URL", BigDecimal.TEN, new CategoryDto(), images, "Type");
		assertEquals(images, post.getImages());
	}

	/**
	 * Test get type
	 */
	@Test
	public void testGetType() {
		String type = "Type";
		post = new PostSummaryDto(1L, "Title", "Description", "URL", BigDecimal.TEN, new CategoryDto(),
				new ArrayList<>(), type);
		assertEquals(type, post.getType());
	}
}
