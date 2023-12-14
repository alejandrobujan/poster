package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostUpdateDto {

	/** The post's author id. */
	@Setter
	@NotNull
	private Long authorId;
	/** The post title. */
	@NotNull
	@Setter
	@Size(min = 1, max = 60)
	private String title;
	/** The post description. */
	@NotNull
	@Setter
	@Size(min = 1, max = 256)
	private String description;
	/** The post url. */
	@Setter
	@Size(min = 0, max = 2048)
	private String url;
	/** The price. */
	@Setter
	@NotNull
	private BigDecimal price;
	/** The post category. */
	@Setter
	private Long categoryId;
	/** Images related to the post. */
	@NotNull
	private List<byte[]> images = new ArrayList<>();
	/** The type of the post */
	@NotNull
	@Setter
	private String type;
	/** The properties of the post */
	private Map<String, String> properties;
	/** The expiration date */
	@Setter
	@NotNull
	private Long expirationDate;

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
			List<byte[]> images, String type, Map<String, String> properties, Long expirationDate) {
		super();
		this.authorId = authorId;
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.categoryId = categoryId;
		this.images = new ArrayList<>(images);
		this.type = type;
		this.expirationDate = expirationDate;
		this.properties = new HashMap<>(properties);
	}

	/**
	 * Sets images related to the post.
	 * 
	 * @param images the images to set
	 */
	public void setImages(List<byte[]> images) {
		this.images = new ArrayList<>(images);
	}

	/**
	 * Sets the properties of the post
	 * 
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = new HashMap<>(properties);
	}

}
