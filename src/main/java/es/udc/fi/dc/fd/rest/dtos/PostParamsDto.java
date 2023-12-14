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

/**
 * The class PostParamsDto
 */
@Getter
public class PostParamsDto {

	/** The type of the post */
	@Setter
	@NotNull
	private String type;
	/** The post title. */
	@Setter
	@NotNull
	@Size(min = 1, max = 60)
	private String title;
	/** The post url. */
	@Setter
	@Size(min = 0, max = 2048)
	private String url;
	/** The post description. */
	@Setter
	@NotNull
	@Size(min = 1, max = 256)
	private String description;
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
	/** The properties of the post */
	private Map<String, String> properties;
	/** The expiration date */
	@Setter
	@NotNull
	private Long expirationDate;

	/**
	 * Sets the properties of the post
	 * 
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
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
		this.images = new ArrayList<>(images);
		this.type = type;
		this.expirationDate = expirationDate;
		this.properties = new HashMap<>(properties);

	}

}
