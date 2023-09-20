/**
 * 
 */
package es.udc.fi.dc.fd.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import es.udc.fi.dc.fd.model.entities.Category;

/**
 * The class CategoryConversor.
 */
public class CategoryConversor {
	
	
	private CategoryConversor () {}
	
	public final static CategoryDto toCategoryDto(Category category) {
		return new CategoryDto(category.getId(), category.getName());
	}
	
	public final static List<CategoryDto> toCategoryDtos(List<Category> categories) {
		return categories.stream().map(c -> toCategoryDto(c)).collect(Collectors.toList());
	}
	
}
