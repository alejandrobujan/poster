package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The class RatePostDto
 */
@Getter
@Setter
@AllArgsConstructor
public class RatePostDto {

	/** The number of positive ratings */
	@NotNull
	private int positiveCount;

	/** The number of negative ratings */
	@NotNull
	private int negativeCount;

}
