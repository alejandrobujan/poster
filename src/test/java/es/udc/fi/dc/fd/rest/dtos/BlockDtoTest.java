package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * The class BlockDtoTest
 */
public class BlockDtoTest {

	/**
	 * Test default constructor
	 */
	@Test
	public void testDefaultConstructor() {
		BlockDto<String> blockDto = new BlockDto<>();
		assertNotNull(blockDto);
		assertNull(blockDto.getItems());
		assertFalse(blockDto.getExistMoreItems());
	}

	/**
	 * Test parametrized constructor
	 */
	@Test
	public void testParametrizedConstructor() {
		List<Integer> items = Arrays.asList(1, 2, 3);
		boolean existMoreItems = true;

		BlockDto<Integer> blockDto = new BlockDto<>(items, existMoreItems);
		assertNotNull(blockDto);
		assertEquals(items, blockDto.getItems());
		assertEquals(existMoreItems, blockDto.getExistMoreItems());
	}

	/**
	 * Test get items
	 */
	@Test
	public void testGetItems() {
		List<String> items = new ArrayList<>();
		items.add("Item 1");
		items.add("Item 2");

		BlockDto<String> blockDto = new BlockDto<>(items, false);
		assertEquals(items, blockDto.getItems());
	}

	/**
	 * Test set items
	 */
	@Test
	public void testSetItems() {
		BlockDto<Character> blockDto = new BlockDto<>();
		List<Character> items = Arrays.asList('a', 'b', 'c');

		blockDto.setItems(items);
		assertEquals(items, blockDto.getItems());
	}

	/**
	 * Test get exist more items
	 */
	@Test
	public void testGetExistMoreItems() {
		BlockDto<Boolean> blockDto = new BlockDto<>();
		blockDto.setExistMoreItems(true);

		assertTrue(blockDto.getExistMoreItems());
	}

	/**
	 * Test set exists more items
	 */
	@Test
	public void testSetExistMoreItems() {
		BlockDto<Double> blockDto = new BlockDto<>();
		blockDto.setExistMoreItems(false);

		assertFalse(blockDto.getExistMoreItems());
	}
}
