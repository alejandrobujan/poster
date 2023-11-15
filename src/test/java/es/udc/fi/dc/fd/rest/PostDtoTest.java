package es.udc.fi.dc.fd.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import es.udc.fi.dc.fd.rest.dtos.CategoryDto;
import es.udc.fi.dc.fd.rest.dtos.PostDto;
import es.udc.fi.dc.fd.rest.dtos.UserSummaryDto;

public class PostDtoTest {

	private UserSummaryDto userSummaryDto;
	private CategoryDto categoryDto;

	@BeforeEach
	public void setUp() {
		userSummaryDto = new UserSummaryDto(1L, "username", "Sample", "User", new byte[] { 1, 2, 3 });
		categoryDto = new CategoryDto(1L, "Travel");
	}

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
		boolean expired = false;
		Long validationDate = null;
		String type = "Test Type";
		Map<String, String> properties = new HashMap<>();

		PostDto parametrizedDto = new PostDto(id, title, description, url, price, categoryDto, userSummaryDto, images,
				creationDate, positiveRatings, negativeRatings, expired, validationDate, type, properties);
		assertNotNull(parametrizedDto);
	}

	@Test
	public void testGetId() {
		Long id = 1L;
		PostDto postDto = new PostDto(id, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(id, postDto.getId());
	}

	@Test
	public void testGetTitle() {
		String title = "Test Title";
		PostDto postDto = new PostDto(1L, title, "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(title, postDto.getTitle());
	}

	@Test
	public void testGetDescription() {
		String description = "Test Description";
		PostDto postDto = new PostDto(1L, "Title", description, "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(description, postDto.getDescription());
	}

	@Test
	public void testGetUrl() {
		String url = "http://example.com";
		PostDto postDto = new PostDto(1L, "Title", "Description", url, BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(url, postDto.getUrl());
	}

	@Test
	public void testGetPrice() {
		BigDecimal price = new BigDecimal("99.99");
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", price, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(price, postDto.getPrice());
	}

	@Test
	public void testGetCategoryDto() {
		CategoryDto categoryDto = new CategoryDto();
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(categoryDto, postDto.getCategoryDto());
	}

	@Test
	public void testGetUserSummaryDto() {
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(userSummaryDto, postDto.getUserSummaryDto());
	}

	@Test
	public void testGetImages() {
		List<byte[]> images = new ArrayList<>();
		images.add(new byte[] { 1, 2, 3 });
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				images, System.currentTimeMillis(), 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(images, postDto.getImages());
	}

	@Test
	public void testGetPositiveRatings() {
		int positiveRatings = 5;
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), positiveRatings, 0, false, null, "Type",
				new HashMap<>());

		assertEquals(positiveRatings, postDto.getPositiveRatings());
	}

	@Test
	public void testGetNegativeRatings() {
		int negativeRatings = 3;
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, negativeRatings, false, null, "Type",
				new HashMap<>());

		assertEquals(negativeRatings, postDto.getNegativeRatings());
	}

	@Test
	public void testIsExpired() {
		boolean expired = true;
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, expired, null, "Type", new HashMap<>());

		assertEquals(expired, postDto.isExpired());
	}

	@Test
	public void testGetCreationDate() {
		Long creationDate = System.currentTimeMillis();
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), creationDate, 0, 0, false, null, "Type", new HashMap<>());

		assertEquals(creationDate, postDto.getCreationDate());
	}

	@Test
	public void testGetType() {
		String type = "Test Type";
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, type, new HashMap<>());

		assertEquals(type, postDto.getType());
	}

	@Test
	public void testGetProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("key", "value");
		PostDto postDto = new PostDto(1L, "Title", "Description", "URL", BigDecimal.ZERO, categoryDto, userSummaryDto,
				new ArrayList<>(), System.currentTimeMillis(), 0, 0, false, null, "Type", properties);

		assertEquals(properties, postDto.getProperties());
	}

	@Test
	public void testSetId() {
		Long id = 1L;
		PostDto postDto = new PostDto();
		postDto.setId(id);

		assertEquals(id, postDto.getId());
	}

	@Test
	public void testSetTitle() {
		String title = "Test Title";
		PostDto postDto = new PostDto();
		postDto.setTitle(title);

		assertEquals(title, postDto.getTitle());
	}

	@Test
	public void testSetDescription() {
		String description = "Test Description";
		PostDto postDto = new PostDto();
		postDto.setDescription(description);

		assertEquals(description, postDto.getDescription());
	}

	@Test
	public void testSetUrl() {
		String url = "http://example.com";
		PostDto postDto = new PostDto();
		postDto.setUrl(url);

		assertEquals(url, postDto.getUrl());
	}

	@Test
	public void testSetPrice() {
		BigDecimal price = new BigDecimal("99.99");
		PostDto postDto = new PostDto();
		postDto.setPrice(price);

		assertEquals(price, postDto.getPrice());
	}

	@Test
	public void testSetCategoryDto() {
		CategoryDto categoryDto = new CategoryDto();
		PostDto postDto = new PostDto();
		postDto.setCategoryDto(categoryDto);

		assertEquals(categoryDto, postDto.getCategoryDto());
	}

	@Test
	public void testSetUserSummaryDto() {
		PostDto postDto = new PostDto();
		postDto.setUserSummaryDto(userSummaryDto);

		assertEquals(userSummaryDto, postDto.getUserSummaryDto());
	}

	@Test
	public void testSetImages() {
		List<byte[]> images = new ArrayList<>();
		images.add(new byte[] { 1, 2, 3 });
		PostDto postDto = new PostDto();
		postDto.setImages(images);

		assertEquals(images, postDto.getImages());
	}

	@Test
	public void testSetPositiveRatings() {
		int positiveRatings = 5;
		PostDto postDto = new PostDto();
		postDto.setPositiveRatings(positiveRatings);

		assertEquals(positiveRatings, postDto.getPositiveRatings());
	}

	@Test
	public void testSetNegativeRatings() {
		int negativeRatings = 3;
		PostDto postDto = new PostDto();
		postDto.setNegativeRatings(negativeRatings);

		assertEquals(negativeRatings, postDto.getNegativeRatings());
	}

	@Test
	public void testSetExpired() {
		boolean expired = true;
		PostDto postDto = new PostDto();
		postDto.setExpired(expired);

		assertEquals(expired, postDto.isExpired());
	}

	@Test
	public void testSetCreationDate() {
		Long creationDate = System.currentTimeMillis();
		PostDto postDto = new PostDto();
		postDto.setCreationDate(creationDate);

		assertEquals(creationDate, postDto.getCreationDate());
	}

	@Test
	public void testSetType() {
		String type = "Test Type";
		PostDto postDto = new PostDto();
		postDto.setType(type);

		assertEquals(type, postDto.getType());
	}

	@Test
	public void testSetProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("key", "value");
		PostDto postDto = new PostDto();
		postDto.setProperties(properties);

		assertEquals(properties, postDto.getProperties());
	}
}