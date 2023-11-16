package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

/**
 * The class PostDto
 */
@Getter
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
	/** The validation date */
	private Long validationDate;
	/** The type of the post */
	private String type;
	/** The properties of the post */
	private Map<String, String> properties;

	/**
	 * @@param id the id
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
	 * @param validationDate  the validation date
	 * @param type            the post type
	 * @param properties      the properties of the post
	 */
	public PostDto(Long id, String title, String description, String url, BigDecimal price, CategoryDto categoryDto,
			UserSummaryDto userCreatorDto, List<byte[]> images, Long creationDate, int positiveRatings,
			int negativeRatings, boolean expired, Long validationDate, String type, Map<String, String> properties) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.price = price;
		this.categoryDto = categoryDto;
		this.userSummaryDto = userCreatorDto;
		this.images = new ArrayList<>(images);
		this.creationDate = creationDate;
		this.positiveRatings = positiveRatings;
		this.negativeRatings = negativeRatings;
		this.expired = expired;
		this.validationDate = validationDate;
		this.type = type;
		this.properties = new HashMap<>(properties);
	}

	public PostDto() {
	}

}
