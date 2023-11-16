package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The class PostSummaryDto
 */
@Getter
@Setter
@AllArgsConstructor
public class PostSummaryDto {

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
	/** Images related to the post. */
	private List<byte[]> images = new ArrayList<>();
	/** The post type */
	private String type;

	/**
	 * Instantiates a new post summary dto.
	 */
	public PostSummaryDto() {
	}
}
