package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The class PostDto
 */
@Getter
@AllArgsConstructor
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

	public PostDto() {
	}

}
