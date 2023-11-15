package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockTest {

	private List<String> items;
	private boolean existMoreItems;

	@BeforeEach
	public void setUp() {
		items = Arrays.asList("Item 1", "Item 2", "Item 3");
		existMoreItems = true;
	}

	@Test
	public void testGetItems() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertEquals(items, block.getItems());
	}

	@Test
	public void testGetExistMoreItems() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertTrue(block.getExistMoreItems());
	}

	@Test
	public void testHashCode() {
		Block<String> block1 = new Block<>(items, existMoreItems);
		Block<String> block2 = new Block<>(items, existMoreItems);

		assertEquals(block1.hashCode(), block2.hashCode());
	}

	@Test
	public void testEquals() {
		Block<String> block1 = new Block<>(items, existMoreItems);
		Block<String> block2 = new Block<>(items, existMoreItems);
		Block<String> block3 = new Block<>(Arrays.asList("Item 1", "Item 2"), false);

		assertTrue(block1.equals(block2));
		assertFalse(block1.equals(block3));
	}

	@Test
	public void testEqualsSameInstance() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertTrue(block.equals(block));
	}

	@Test
	public void testEqualsDifferentObject() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertFalse(block.equals(mock(Object.class)));
	}
}
