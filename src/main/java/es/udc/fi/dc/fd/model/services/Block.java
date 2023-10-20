package es.udc.fi.dc.fd.model.services;

import java.util.List;

/**
 * The Class Block.
 */
public class Block<T> {

	/** the list of items */
	private List<T> items;
	/** if exist more items or not */
	private boolean existMoreItems;

	/**
	 * Instantiates a new block.
	 *
	 * @param items          the list of items
	 * @param existMoreItems if exist more items or not
	 */
	public Block(List<T> items, boolean existMoreItems) {

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
	 * Gets if there are more items or not
	 * 
	 * @return if there are more items or not
	 */
	public boolean getExistMoreItems() {
		return existMoreItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (existMoreItems ? 1231 : 1237);
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Block other = (Block) obj;
		if (existMoreItems != other.existMoreItems)
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

}
