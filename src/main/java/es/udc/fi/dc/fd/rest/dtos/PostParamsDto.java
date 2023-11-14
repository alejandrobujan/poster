package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * The class PostParamsDto
 */
public class PostParamsDto {

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
	@NotNull
	private Long expirationDate;

	/**
	 * Instantiates a new post params dto.
	 */
	public PostParamsDto() {
	}

	/**
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url of the post
	 * @param price       the price of the post
	 * @param categoryId  the category id of the post
	 * @param images      the images of the post
	 * @param type        the post type
	 * @param properties  the properties of the post
	 */
	public PostParamsDto(String title, String description, String url, BigDecimal price, Long categoryId,
			List<byte[]> images, String type, Map<String, String> properties, Long expirationDate) {
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.categoryId = categoryId;
		this.images = images;
		this.type = type;
		this.properties = properties;
		this.expirationDate = expirationDate;
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
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the category id.
	 * 
	 * @param category the category id to set
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
	 * @return the expirationDate
	 */
	public Long getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Long expirationDate) {
		this.expirationDate = expirationDate;
	}

}
