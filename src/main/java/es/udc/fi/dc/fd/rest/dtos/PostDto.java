/**
 * 
 */
package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.Image;
import es.udc.fi.dc.fd.model.entities.User;

/**
 * The class PostDto
 */
public class PostDto {
	
	/** The post id*/
	private Long id;
	/** The post title. */
	private String title;
	/** The post description. */
	private String description;
	/** The post url. */
	private String url;
	/** The price. */
	private BigDecimal price;
	/** The post author. */
	private User user;
	/** The post category. */
	private Category category;
	/** Images related to the post. */
	private Set<Image> images = new HashSet<>();
	
	/**
	 * Instantiates a new post dto. 
	 */
	public PostDto () {}
	
	/**
	 * @param id the id
	 * @param title of the post
	 * @param description of the post
	 * @param url of the post
	 * @param price of the post
	 * @param user of the post
	 * @param category of the post
	 * @param images of the post
	 */
	public PostDto(Long id, String title, String description, String url, BigDecimal price, User user,
			Category category, Set<Image> images) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.user = user;
		this.category = category;
		this.images = images;
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
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * Sets the user.
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * Sets the category.
	 * 
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	/**
	 * Gets the images.
	 * 
	 * @return the images
	 */
	public Set<Image> getImages() {
		return images;
	}
	/**
	 * Sets the images.
	 * 
	 * @param images the images to set
	 */
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	
	
	
	

}
