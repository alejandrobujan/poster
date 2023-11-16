package es.udc.fi.dc.fd.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.udc.fi.dc.fd.rest.dtos.CategoryDto;
import es.udc.fi.dc.fd.rest.dtos.PostSummaryDto;

public class PostSummaryDtoTest {

	private PostSummaryDto post;

	@BeforeEach
	public void setUp() {
		post = new PostSummaryDto();
	}

	@Test
	public void testDefaultConstructor() {
		assertNotNull(post);
	}

	@Test
	public void testParametrizedConstructor() {
		post = new PostSummaryDto(1L, "Title", "Description", "URL", BigDecimal.TEN, new CategoryDto(),
				new ArrayList<>(), "Type");
		assertNotNull(post);
	}

	@Test
	public void testGetId() {
		Long id = 1L;
		post = new PostSummaryDto(id, "Title", "Description", "URL", BigDecimal.TEN, new CategoryDto(),
				new ArrayList<>(), "Type");
		assertEquals(id, post.getId());
	}

	@Test
	public void testGetTitle() {
		String title = "Title";
		post = new PostSummaryDto(1L, title, "Description", "URL", BigDecimal.TEN, new CategoryDto(), new ArrayList<>(),
				"Type");
		assertEquals(title, post.getTitle());
	}

	@Test
	public void testGetDescription() {
		String description = "Description";
		post = new PostSummaryDto(1L, "Title", description, "URL", BigDecimal.TEN, new CategoryDto(), new ArrayList<>(),
				"Type");
		assertEquals(description, post.getDescription());
	}

	@Test
	public void testGetUrl() {
		String url = "URL";
		post = new PostSummaryDto(1L, "Title", "Description", url, BigDecimal.TEN, new CategoryDto(), new ArrayList<>(),
				"Type");
		assertEquals(url, post.getUrl());
	}

	@Test
	public void testGetPrice() {
		BigDecimal price = BigDecimal.TEN;
		post = new PostSummaryDto(1L, "Title", "Description", "URL", price, new CategoryDto(), new ArrayList<>(),
				"Type");
		assertEquals(price, post.getPrice());
	}

	@Test
	public void testGetCategoryDto() {
		CategoryDto categoryDto = new CategoryDto();
		post = new PostSummaryDto(1L, "Title", "Description", "URL", BigDecimal.TEN, categoryDto, new ArrayList<>(),
				"Type");
		assertEquals(categoryDto, post.getCategoryDto());
	}

	@Test
	public void testGetImages() {
		List<byte[]> images = new ArrayList<>();
		images.add(new byte[] { 1, 2, 3 });
		images.add(new byte[] { 4, 5, 6 });
		post = new PostSummaryDto(1L, "Title", "Description", "URL", BigDecimal.TEN, new CategoryDto(), images, "Type");
		assertEquals(images, post.getImages());
	}

	@Test
	public void testGetType() {
		String type = "Type";
		post = new PostSummaryDto(1L, "Title", "Description", "URL", BigDecimal.TEN, new CategoryDto(),
				new ArrayList<>(), type);
		assertEquals(type, post.getType());
	}
}
