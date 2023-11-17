package es.udc.fi.dc.fd.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.udc.fi.dc.fd.rest.dtos.SearchFiltersDto;

public class SearchFiltersDtoTest {

	private SearchFiltersDto searchFiltersDto;

	@BeforeEach
	public void setUp() {
		Long categoryId = 1L;
		String type = "coupon";
		Map<String, BigDecimal> price = new HashMap<>();
		price.put("gt", BigDecimal.valueOf(50.0));
		String date = "2023-11-16";
		boolean expired = false;
		String sortParam = "param";
		String sortOrder = "asc";

		searchFiltersDto = new SearchFiltersDto(categoryId, type, price, date, expired, sortParam, sortOrder);
	}

	@Test
	public void testParametrizedConstructorNotNull() {
		Long categoryId = 1L;
		String type = "Coupon";
		Map<String, BigDecimal> price = new HashMap<>();
		price.put("USD", BigDecimal.valueOf(50.0));
		String date = "2023-11-16";
		boolean expired = false;
		String sortParam = "param";
		String sortOrder = "asc";

		SearchFiltersDto searchFiltersDto = new SearchFiltersDto(categoryId, type, price, date, expired, sortParam,
				sortOrder);
		Assertions.assertNotNull(searchFiltersDto);
	}

	@Test
	public void testSetCategoryId() {
		Long categoryId = 1L;
		searchFiltersDto.setCategoryId(categoryId);
		Assertions.assertEquals(categoryId, searchFiltersDto.getCategoryId());
	}

	@Test
	public void testSetType() {
		String type = "Offer";
		searchFiltersDto.setType(type);
		Assertions.assertEquals(type, searchFiltersDto.getType());
	}

	@Test
	public void testSetPrice() {
		Map<String, BigDecimal> price = new HashMap<>();
		price.put("gt", BigDecimal.valueOf(70.0));
		searchFiltersDto.setPrice(price);
		Assertions.assertEquals(price, searchFiltersDto.getPrice());
	}

	@Test
	public void testSetDate() {
		String date = "2024-11-16";
		searchFiltersDto.setDate(date);
		Assertions.assertEquals(date, searchFiltersDto.getDate());
	}

	@Test
	public void testSetExpired() {
		boolean expired = true;
		searchFiltersDto.setExpired(expired);
		Assertions.assertTrue(searchFiltersDto.isExpired());
	}

	@Test
	public void testSetSortParam() {
		String sortParam = "param";
		searchFiltersDto.setSortParam(sortParam);
		Assertions.assertEquals(sortParam, searchFiltersDto.getSortParam());
	}

	@Test
	public void testSetSortOrder() {
		String sortOrder = "asc";
		searchFiltersDto.setSortOrder(sortOrder);
		Assertions.assertEquals(sortOrder, searchFiltersDto.getSortOrder());
	}
}
