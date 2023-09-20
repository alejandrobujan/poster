package es.udc.fi.dc.fd.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Post {
	
	/**The id.*/
	private Long id;
	/**The post title.*/
	private String title;
	/**The post description.*/
	private String description;
	/**The post url.*/
	private String url;
	/**The price.*/
	private BigDecimal price;
	/**The creation date.*/
	private LocalDateTime creationDate;
	/**The post author.*/
	private User userId;
	/**The post category.*/
	private Category categoryId;
	
	/**
	 * Instantiates a new post.
	 */
	public Post() {
	}

	/**
	 * Gets the id
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	 * Gets the post title
	 * 
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the post title.
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
	 * Gets the creation date.
	 * 
	 * @return the creationDate
	 */
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the post author.
	 * 
	 * @return the userId
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User getUserId() {
		return userId;
	}

	/**
	 * Sets the post author.
	 * 
	 * @param userId the userId to set
	 */
	public void setUserId(User userId) {
		this.userId = userId;
	}

	/**
	 * Gets the post category.
	 * 
	 * @return the categoryId
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	public Category getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the post category.
	 * 
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}
	
	

}
