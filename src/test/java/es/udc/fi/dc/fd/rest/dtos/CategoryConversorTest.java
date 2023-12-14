package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import es.udc.fi.dc.fd.model.entities.Category;

/**
 * The class CategoryConversorTest
 */
public class CategoryConversorTest {

	/**
	 * Test to category dto
	 */
	@Test
	public void testToCategoryDto() {
		Category category = new Category(1L, "Example");
		CategoryDto categoryDto = CategoryConversor.toCategoryDto(category);

		assertEquals(category.getId(), categoryDto.getId());
		assertEquals(category.getName(), categoryDto.getName());
	}

	/**
	 * Test to category dto null
	 */
	@Test
	public void testToCategoryDtoNull() {
		CategoryDto categoryDto = CategoryConversor.toCategoryDto(null);

		assertNull(categoryDto.getId());
		assertNull(categoryDto.getName());
	}

	/**
	 * Test to category dtos
	 */
	@Test
	public void testToCategoryDtos() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1L, "Category 1"));
		categories.add(new Category(2L, "Category 2"));

		List<CategoryDto> categoryDtos = CategoryConversor.toCategoryDtos(categories);

		assertEquals(categories.size(), categoryDtos.size());
		for (int i = 0; i < categories.size(); i++) {
			assertEquals(categories.get(i).getId(), categoryDtos.get(i).getId());
			assertEquals(categories.get(i).getName(), categoryDtos.get(i).getName());
		}
	}

	/**
	 * Test to category dtos empty list
	 */
	@Test
	public void testToCategoryDtosEmptyList() {
		List<Category> categories = new ArrayList<>();
		List<CategoryDto> categoryDtos = CategoryConversor.toCategoryDtos(categories);

		assertEquals(0, categoryDtos.size());
	}
}
