package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The class PostSummaryDto
 */
public class PostSummaryDto {

	/** The post id. */
	private Long id;
	/** The post title. */
	private String title;
	/** The post description. */
	private String description;
	/** The post url. */
	private String url;
	/** The price. */
	private BigDecimal price;
	/** The post category. */
	private CategoryDto categoryDto;
	/** Images related to the post. */
	private List<byte[]> images = new ArrayList<>();
	/** The post type */
	private String type;

	/**
	 * Instantiates a new post summary dto.
	 */
	public PostSummaryDto() {
	}

	/**
	 * Instantiates a new post summary dto.
	 * 
	 * @param id          the id of the post
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param categoryDto the categoryDto of the post
	 * @param images      the images of the post
	 * @param type        the type of the psot
	 */
	public PostSummaryDto(Long id, String title, String description, String url, BigDecimal price,
			CategoryDto categoryDto, List<byte[]> images, String type) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.categoryDto = categoryDto;
		this.images = images;
		this.type = type;
	}

	/**
	 * Gets the id
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the post title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the post title
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the post description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the post description.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the post url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the post url.
	 * 
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the price.
	 * 
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 * 
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Gets the categoryDto.
	 *
	 * @return the categoryDto
	 */
	public CategoryDto getCategoryDto() {
		return categoryDto;
	}

	/**
	 * Sets the categoryDto.
	 * 
	 * @param categoryDto the categoryDto to set
	 */
	public void setCategoryDto(CategoryDto categoryDto) {
		this.categoryDto = categoryDto;
	}

	/**
	 * Gets images related to the post.
	 * 
	 * @return the images
	 */
	public List<byte[]> getImages() {
		return images;
	}

	/**
	 * Sets images related to the post.
	 * 
	 * @param images the images to set
	 */
	public void setImages(List<byte[]> images) {
		this.images = images;
	}

	/**
	 * Gets the post type
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the post type
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
