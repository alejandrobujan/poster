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

	/** List of items */
	private List<String> items;
	/** If exists more items */
	private boolean existMoreItems;

	/**
	 * Set up.
	 */
	@BeforeEach
	public void setUp() {
		items = Arrays.asList("Item 1", "Item 2", "Item 3");
		existMoreItems = true;
	}

	/**
	 * Test get items.
	 */
	@Test
	public void testGetItems() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertEquals(items, block.getItems());
	}

	/**
	 * Test get exist more items.
	 */
	@Test
	public void testGetExistMoreItems() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertTrue(block.getExistMoreItems());
	}

	/**
	 * Test hash code.
	 */
	@Test
	public void testHashCode() {
		Block<String> block1 = new Block<>(items, existMoreItems);
		Block<String> block2 = new Block<>(items, existMoreItems);

		assertEquals(block1.hashCode(), block2.hashCode());
	}

	/**
	 * Test equals same instance.
	 */
	@Test
	public void testEqualsSameInstance() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertTrue(block.equals(block));
	}

	/**
	 * Test equals same values.
	 */
	@Test
	public void testEqualsSameValues() {
		Block<String> block1 = new Block<>(items, existMoreItems);
		Block<String> block2 = new Block<>(items, existMoreItems);
		assertTrue(block1.equals(block2));
	}

	/**
	 * Test equals different values.
	 */
	@Test
	public void testEqualsDifferentValues() {
		Block<String> block1 = new Block<>(items, existMoreItems);
		Block<String> block2 = new Block<>(Arrays.asList("Item 1", "Item 2"), false);
		assertFalse(block1.equals(block2));
	}

	/**
	 * Test equals different object.
	 */
	@Test
	public void testEqualsDifferentObject() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertFalse(block.equals(mock(Object.class)));
	}

	/**
	 * Test equals null.
	 */
	@Test
	public void testEqualsNull() {
		Block<String> block = new Block<>(items, existMoreItems);
		assertFalse(block.equals(null));
	}
}
