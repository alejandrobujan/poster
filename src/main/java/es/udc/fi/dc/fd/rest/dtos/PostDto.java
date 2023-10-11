package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 * @param id
	 * @param title
	 * @param description
	 * @param url
	 * @param price
	 * @param categoryDto
	 * @param userCreatorDto
	 * @param images
	 * @param creationDate
	 * @param positiveRatings
	 * @param negativeRatings
	 * @param expired
	 * @param type
	 * @param properties
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the categoryDto
	 */
	public CategoryDto getCategoryDto() {
		return categoryDto;
	}

	/**
	 * @param categoryDto the categoryDto to set
	 */
	public void setCategoryDto(CategoryDto categoryDto) {
		this.categoryDto = categoryDto;
	}

	/**
	 * @return the userSummaryDto
	 */
	public UserSummaryDto getUserSummaryDto() {
		return userSummaryDto;
	}

	/**
	 * @param userSummaryDto the userSummaryDto to set
	 */
	public void setUserSummaryDto(UserSummaryDto userSummaryDto) {
		this.userSummaryDto = userSummaryDto;
	}

	/**
	 * @return the images
	 */
	public List<byte[]> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<byte[]> images) {
		this.images = images;
	}

	/**
	 * @return the positiveRatings
	 */
	public int getPositiveRatings() {
		return positiveRatings;
	}

	/**
	 * @param positiveRatings the positiveRatings to set
	 */
	public void setPositiveRatings(int positiveRatings) {
		this.positiveRatings = positiveRatings;
	}

	/**
	 * @return the negativeRatings
	 */
	public int getNegativeRatings() {
		return negativeRatings;
	}

	/**
	 * @param negativeRatings the negativeRatings to set
	 */
	public void setNegativeRatings(int negativeRatings) {
		this.negativeRatings = negativeRatings;
	}

	/**
	 * @return the expired
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * @param expired the expired to set
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	/**
	 * @return the creationDate
	 */
	public Long getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
