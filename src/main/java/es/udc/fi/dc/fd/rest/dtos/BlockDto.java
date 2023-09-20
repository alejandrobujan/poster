package es.udc.fi.dc.fd.rest.dtos;

import java.util.List;

public class BlockDto<T> {
	
	private List<T> items;
    private boolean existMoreItems;
    
    public BlockDto() {}

    public BlockDto(List<T> items, boolean existMoreItems) {
        
        this.items = items;
        this.existMoreItems = existMoreItems;

    }
    
    public List<T> getItems() {
        return items;
    }
    
    public void setItems(List<T> items) {
		this.items = items;
	}
    
	public boolean getExistMoreItems() {
        return existMoreItems;
    }
	
	public void setExistMoreItems(boolean existMoreItems) {
		this.existMoreItems = existMoreItems;
	}
    
}