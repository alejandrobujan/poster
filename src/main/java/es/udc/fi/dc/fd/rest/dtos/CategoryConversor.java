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

	/**
	 * Instantiates a new category conversor.
	 */
	private CategoryConversor() {
	}

	/**
	 * To category dto.
	 * 
	 * @param category the category
	 * @return the category dto
	 */
	public final static CategoryDto toCategoryDto(Category category) {
		return new CategoryDto(category != null ? category.getId() : null,
				category != null ? category.getName() : null);
	}

	/**
	 * To category dtos.
	 * 
	 * @param categories the list of categories
	 * @return the list of category dto
	 */
	public final static List<CategoryDto> toCategoryDtos(List<Category> categories) {
		return categories.stream().map(c -> toCategoryDto(c)).collect(Collectors.toList());
	}

}
