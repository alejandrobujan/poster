package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * Instantiates a new post dto. 
	 */
	public PostParamsDto () {}
	
	/**
	 * @param title the title of the post
	 * @param description the description of the post
	 * @param url the url of the post
	 * @param price the price of the post
	 * @param categoryId the category id of the post
	 * @param images the images of the post
	 */
	public PostParamsDto(String title, String description, String url, BigDecimal price,
			Long categoryId, List<byte[]> images) {
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.categoryId = categoryId;
		this.images = images;
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
