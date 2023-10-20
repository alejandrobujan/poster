package es.udc.fi.dc.fd.rest.dtos;

/**
 * The class CategoryDto
 */
public class CategoryDto {

	/** The id. */
	private Long id;
	/** The category name. */
	private String name;

	/**
	 * Instantiates a new category dto.
	 */
	public CategoryDto() {
	}

	/**
	 * @param id   the id
	 * @param name the category name
	 */
	public CategoryDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
