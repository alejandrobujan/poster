package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * The class PostDtoTest
 */
public class PostDtoTest {

	/** The user summary dto */
	private UserSummaryDto userSummaryDto;
	/** The category dto */
	private CategoryDto categoryDto;

	/**
	 * Set up
	 */
	@BeforeEach
	public void setUp() {
		userSummaryDto = new UserSummaryDto(1L, "username", "Sample", "User", new byte[] { 1, 2, 3 });
		categoryDto = new CategoryDto(1L, "Travel");
	}

	/**
	 * Test parametrized constructor
	 */
	@Test
	public void testParametrizedConstructor() {
		Long id = 1L;
		String title = "Test Title";
		String description = "Test Description";
		String url = "http://example.com";
		BigDecimal price = new BigDecimal("99.99");
		List<byte[]> images = new ArrayList<>();
		Long creationDate = System.currentTimeMillis();
		int positiveRatings = 5;
		int negativeRatings = 2;
		Long expirationDate = System.currentTimeMillis();
		Long validationDate = null;
		String type = "Test Type";
		Map<String, String> properties = new HashMap<>();

		PostDto parametrizedDto = new PostDto(id, title, description, url, price, categoryDto, userSummaryDto, images,
				creationDate, positiveRatings, negativeRatings, validationDate, type, properties, expirationDate);
		assertNotNull(parametrizedDto);
	}

	/** Test get id */
	@Test
	public void testGetId() {
		Long id = 1L;
		PostDto postDto = new PostDto(id, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(id, postDto.getId());
	}

	/** Test get title */
	@Test
	public void testGetTitle() {
		String title = "Test Title";
		PostDto postDto = new PostDto(1L, title, "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(title, postDto.getTitle());
	}

	/** Test get description */
	@Test
	public void testGetDescription() {
		String description = "Test Description";
		PostDto postDto = new PostDto(1L, "Title", description, "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(description, postDto.getDescription());
	}

	/** Test get url */
	@Test
	public void testGetUrl() {
		String url = "http://example.com";
		PostDto postDto = new PostDto(1L, "Title", "Description", url, BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(url, postDto.getUrl());
	}

	/** Test get price */
	@Test
	public void testGetPrice() {
		BigDecimal price = new BigDecimal("99.99");
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", price, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(price, postDto.getPrice());
	}

	/** Test get category dto */
	@Test
	public void testGetCategoryDto() {
		CategoryDto categoryDto = new CategoryDto();
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(categoryDto, postDto.getCategoryDto());
	}

	/** Test get user summaty dto */
	@Test
	public void testGetUserSummaryDto() {
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(userSummaryDto, postDto.getUserSummaryDto());
	}

	/** Test get images */
	@Test
	public void testGetImages() {
		List<byte[]> images = new ArrayList<>();
		images.add(new byte[] { 1, 2, 3 });
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				images, System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(), System.currentTimeMillis());

		assertEquals(images, postDto.getImages());
	}

	/** Test get positive ratings */
	@Test
	public void testGetPositiveRatings() {
		int positiveRatings = 5;
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), positiveRatings, 0, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(positiveRatings, postDto.getPositiveRatings());
	}

	/** Test get negative ratings */
	@Test
	public void testGetNegativeRatings() {
		int negativeRatings = 3;
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, negativeRatings, null, "Type", new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(negativeRatings, postDto.getNegativeRatings());
	}

	/** Test get expiration date */
	@Test
	public void testGetExpirationDate() {
		Long expirationDate = System.currentTimeMillis();
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", new HashMap<>(), expirationDate);

		assertEquals(expirationDate, postDto.getExpirationDate());
	}

	/** Test get creation date */
	@Test
	public void testGetCreationDate() {
		Long creationDate = System.currentTimeMillis();
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), creationDate, 0, 0, null, "Type", new HashMap<>(), System.currentTimeMillis());

		assertEquals(creationDate, postDto.getCreationDate());
	}

	/** Test get type */
	@Test
	public void testGetType() {
		String type = "Test Type";
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, type, new HashMap<>(),
				System.currentTimeMillis());

		assertEquals(type, postDto.getType());
	}

	/** Test get properties */
	@Test
	public void testGetProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("key", "value");
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, null, "Type", properties,
				System.currentTimeMillis());

		assertEquals(properties, postDto.getProperties());
	}

}