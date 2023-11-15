package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class PostDto
 */
public class PostDto {
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
	/** The post user */
	private UserSummaryDto userSummaryDto;
	/** Images related to the post. */
	private List<byte[]> images = new ArrayList<>();
	/** The creation date */
	private Long creationDate;
	/** The positive rating count. */
	private int positiveRatings;
	/** The negative ratings count. */
	private int negativeRatings;
	/** If is expired or not */
	private boolean expired;
	/** The type of the post */
	private String type;
	/** The properties of the post */
	private Map<String, String> properties;

	/**
	 * Instantiates a new post dto.
	 * 
	 * @param id              the id
	 * @param title           the title of the post
	 * @param description     the description of the post
	 * @param url             the url associated to the post
	 * @param price           the price of the post
	 * @param categoryDto     the category dto associated to the post
	 * @param userCreatorDto  the user summary dto from the user who create the post
	 * @param images          the images associated to the post
	 * @param creationDate    the creation date of the post
	 * @param positiveRatings the number of positive ratings of the post
	 * @param negativeRatings the number of negative ratings of the post
	 * @param expired         if the post is expired or not
	 * @param type            the post type
	 * @param properties      the properties of the post
	 */
	public PostDto(Long id, String title, String description, String url, BigDecimal price, CategoryDto categoryDto,
			UserSummaryDto userCreatorDto, List<byte[]> images, Long creationDate, int positiveRatings,
			int negativeRatings, boolean expired, String type, Map<String, String> properties) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.categoryDto = categoryDto;
		this.userSummaryDto = userCreatorDto;
		this.images = images;
		this.creationDate = creationDate;
		this.positiveRatings = positiveRatings;
		this.negativeRatings = negativeRatings;
		this.expired = expired;
		this.type = type;
		this.properties = properties;
	}

	public PostDto() {
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
	 * Gets the category dto.
	 * 
	 * @return the categoryDto
	 */
	public CategoryDto getCategoryDto() {
		return categoryDto;
	}

	/**
	 * Sets the category dto.
	 * 
	 * @param categoryDto the categoryDto to set
	 */
	public void setCategoryDto(CategoryDto categoryDto) {
		this.categoryDto = categoryDto;
	}

	/**
	 * Gets the user summary dto.
	 * 
	 * @return the userSummaryDto
	 */
	public UserSummaryDto getUserSummaryDto() {
		return userSummaryDto;
	}

	/**
	 * Sets the user summary dto.
	 * 
	 * @param userSummaryDto the userSummaryDto to set
	 */
	public void setUserSummaryDto(UserSummaryDto userSummaryDto) {
		this.userSummaryDto = userSummaryDto;
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
	 * Gets the positive ratings
	 * 
	 * @return the positiveRatings
	 */
	public int getPositiveRatings() {
		return positiveRatings;
	}

	/**
	 * Sets the positive ratings
	 * 
	 * @param positiveRatings the positiveRatings to set
	 */
	public void setPositiveRatings(int positiveRatings) {
		this.positiveRatings = positiveRatings;
	}

	/**
	 * Gets the negative ratings
	 * 
	 * @return the negativeRatings
	 */
	public int getNegativeRatings() {
		return negativeRatings;
	}

	/**
	 * Sets the negative ratings
	 * 
	 * @param negativeRatings the negativeRatings to set
	 */
	public void setNegativeRatings(int negativeRatings) {
		this.negativeRatings = negativeRatings;
	}

	/**
	 * Gets if the post is expired or not
	 * 
	 * @return if the post is expired or not
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * Sets the expired attribute
	 * 
	 * @param expired value to set (if it is expired or not)
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creationDate
	 */
	public Long getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
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

}
