package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUpdateDto {

	/** The post's author id. */
	@NotNull
	private Long authorId;
	/** The post title. */
	@NotNull
	@Size(min = 1, max = 60)
	private String title;
	/** The post description. */
	@NotNull
	@Size(min = 1, max = 256)
	private String description;
	/** The post url. */
	@Size(min = 0, max = 2048)
	private String url;
	/** The price. */
	@NotNull
	private BigDecimal price;
	/** The post category. */
	private Long categoryId;
	/** Images related to the post. */
	@NotNull
	private List<byte[]> images = new ArrayList<>();
	/** The type of the post */
	@NotNull
	private String type;
	/** The properties of the post */
	private Map<String, String> properties;

	/**
	 * Instantiates a new post update dto.
	 */
	public PostUpdateDto() {
	}

}
