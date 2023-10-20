package es.udc.fi.dc.fd.rest.dtos;

import java.util.List;

/**
 * The Class Block Dto.
 */
public class BlockDto<T> {

	/** the list of items */
	private List<T> items;
	/** if exist more items or not */
	private boolean existMoreItems;

	/**
	 * Instantiates a new block dto.
	 */
	public BlockDto() {
	}

	/**
	 * Instantiates a new block dto.
	 *
	 * @param items          the list of items
	 * @param existMoreItems if exist more items or not
	 */
	public BlockDto(List<T> items, boolean existMoreItems) {

		this.items = items;
		this.existMoreItems = existMoreItems;

	}

	/**
	 * Gets the list of items
	 * 
	 * @return items the list of items
	 */
	public List<T> getItems() {
		return items;
	}

	/**
	 * Sets the list of items
	 * 
	 * @param items the items to set
	 */
	public void setItems(List<T> items) {
		this.items = items;
	}

	/**
	 * Gets if there are more items or not
	 * 
	 * @return if there are more items or not
	 */
	public boolean getExistMoreItems() {
		return existMoreItems;
	}

	/**
	 * Sets if there are more items or not
	 * 
	 * @param existMoreItems if there are more items or not
	 */
	public void setExistMoreItems(boolean existMoreItems) {
		this.existMoreItems = existMoreItems;
	}

}