package es.udc.fi.dc.fd.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * The Class Post.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Post {

	/** The id. */
	private Long id;
	/** The post title. */
	private String title;
	/** The post description. */
	private String description;
	/** The post url. */
	private String url;
	/** The price. */
	private BigDecimal price;
	/** The creation date. */
	private LocalDateTime creationDate;
	/** The positive rating count. */
	private int positiveRatings;
	/** The negative ratings count. */
	private int negativeRatings;
	/** If is expired or not */
	private LocalDateTime expirationDate;
	/** The post author. */
	private User user;
	/** The post category. */
	private Category category;
	/** Images related to the post. */
	private Set<Image> images = new HashSet<>();

	/**
	 * Instantiates a new post.
	 */
	public Post() {
	}

	/**
	 * Instantiates a new post.
	 * 
	 * @param title        the title
	 * @param description  the description of the post
	 * @param url          the url associated to the post
	 * @param price        the price
	 * @param creationDate the creation date
	 * @param user         the user associated to the post
	 * @param category     the category associated to the post
	 */
	public Post(String title, String description, String url, BigDecimal price, LocalDateTime creationDate, User user,
			Category category, LocalDateTime expirationDate) {
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.creationDate = creationDate;
		this.user = user;
		this.category = category;
		this.positiveRatings = 0;
		this.negativeRatings = 0;
		this.expirationDate = expirationDate;
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
	@Column(columnDefinition = "TIMESTAMP")
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
	public User getUser() {
		return user;
	}

	/**
	 * Sets the post author.
	 * 
	 * @param userId the userId to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the post category.
	 * 
	 * @return the categoryId
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	public Category getCategory() {
		return category;
	}

	/**
	 * Sets the post category.
	 * 
	 * @param categoryId the categoryId to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Gets images related to the post.
	 * 
	 * @return the images
	 */
	@OneToMany(mappedBy = "post")
	public Set<Image> getImages() {
		return images;
	}

	/**
	 * Sets images related to the post.
	 * 
	 * @param images the images to set
	 */
	public void setImages(Set<Image> images) {
		this.images = images;
	}

	/**
	 * Add image.
	 * 
	 * @param image the image to add
	 */
	public void addImage(Image image) {
		images.add(image);
		image.setPost(this);
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
	 * @return the expirationDate
	 */
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", description=" + description + ", url=" + url + ", price="
				+ price + ", creationDate=" + creationDate + ", positiveRatings=" + positiveRatings
				+ ", negativeRatings=" + negativeRatings + ", expirationDate=" + expirationDate + ", user=" + user
				+ ", category=" + category + ", images=" + images + "]";
	}

}
