/**
 * 
 */
package es.udc.fi.dc.fd.rest.dtos;

import es.udc.fi.dc.fd.model.entities.Category;

/**
 * The class CategoryConversor.
 */
public class CategoryConversor {
	
	/**
	 * Instantiates a new Category Conversor
	 */
	private CategoryConversor () {
	}
	
	public static final CategoryDto toCategoryDto (Category category) {
		return new CategoryDto(category.getId(), category.getName());
	}
	
	public static final Category toCategory (CategoryDto categoryDto) {
		return new Category(categoryDto.getId(), categoryDto.getName());
	}
	
}
