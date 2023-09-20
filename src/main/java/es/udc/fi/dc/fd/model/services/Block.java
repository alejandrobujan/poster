package es.udc.fi.dc.fd.model.services;

import java.util.List;

public class Block<T> {
	
	private List<T> items;
    private boolean existMoreItems;

    public Block(List<T> items, boolean existMoreItems) {
        
        this.items = items;
        this.existMoreItems = existMoreItems;

    }
    
    public List<T> getItems() {
        return items;
    }
    
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
