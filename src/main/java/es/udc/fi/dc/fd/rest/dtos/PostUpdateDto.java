package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostUpdateDto {

	/** The post's author id. */
	@NotNull
	private Long authorId;
	/** The post title. */
	@NotNull
	@Size(min = 1, max = 60)
	private String title;
	/** The post description. */
	@NotNull
	@Size(min = 1, max = 256)
	private String description;
	/** The post url. */
	@Size(min = 0, max = 2048)
	private String url;
	/** The price. */
	@NotNull
	private BigDecimal price;
	/** The post category. */
	private Long categoryId;
	/** Images related to the post. */
	@NotNull
	private List<byte[]> images = new ArrayList<>();
	/** The type of the post */
	@NotNull
	private String type;
	/** The properties of the post */
	private Map<String, String> properties;

	/**
	 * Instantiates a new post update dto.
	 */
	public PostUpdateDto() {
	}

	/**
	 * Instantiates a new post update dto.
	 * 
	 * @param authorId    the post's author id
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url of the post
	 * @param price       the price of the post
	 * @param categoryId  the category id of the post
	 * @param images      the images of the post
	 * @param type        the post type
	 * @param properties  the properties of the post
	 */
	public PostUpdateDto(Long authorId, String title, String description, String url, BigDecimal price, Long categoryId,
			List<byte[]> images, String type, Map<String, String> properties) {
		super();
		this.authorId = authorId;
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.categoryId = categoryId;
		this.images = images;
		this.type = type;
		this.properties = properties;
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
	 * Gets the category.
	 *
	 * @return the category
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the category.
	 * 
	 * @param category the category to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	/**
	 * Gets the properties of the post
	 * 
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties of the post
	 * 
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * Gets the post's author id
	 * 
	 * @return the post's author id
	 */
	public Long getAuthorId() {
		return authorId;
	}

	/**
	 * Sets the post's author id
	 * 
	 * @param authorId the post's author id
	 */
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

}
