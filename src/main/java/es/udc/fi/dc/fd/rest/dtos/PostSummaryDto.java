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

	/**
	 * Instantiates a new post dto.
	 */
	public PostSummaryDto() {
	}

	/**
	 * @param id          the id of the post
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url of the post
	 * @param price       the price of the post
	 * @param categoryDto  the categoryDto of the post
	 * @param images      the images of the post
	 */
	public PostSummaryDto(Long id, String title, String description, String url, BigDecimal price, CategoryDto categoryDto,
			List<byte[]> images) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.categoryDto = categoryDto;
		this.images = images;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
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
	 * Gets the images.
	 * 
	 * @return the images
	 */
	public List<byte[]> getImages() {
		return images;
	}

	/**
	 * Sets the images.
	 * 
	 * @param images the images to set
	 */
	public void setImages(List<byte[]> images) {
		this.images = images;
	}
}
