package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;

/**
 * The class RatePostDto
 */
public class RatePostDto {

	/** The number of positive ratings */
	@NotNull
	private int positiveCount;

	/** The number of negative ratings */
	@NotNull
	private int negativeCount;

	/**
	 * Instantiates a new rate post dto.
	 * 
	 * @param positiveCount the number of positive ratings
	 * @param negativeCount the number of negative ratings
	 */
	public RatePostDto(int positiveCount, int negativeCount) {
		this.positiveCount = positiveCount;
		this.negativeCount = negativeCount;
	}

	/**
	 * Gets the number of positive ratings
	 * 
	 * @return the positiveCount
	 */
	public int getPositiveCount() {
		return positiveCount;
	}

	/**
	 * Sets the number of positive ratings
	 * 
	 * @param positiveCount the positiveCount to set
	 */
	public void setPositiveCount(int positiveCount) {
		this.positiveCount = positiveCount;
	}

	/**
	 * Gets the number of negative ratings
	 * 
	 * @return the negativeCount
	 */
	public int getNegativeCount() {
		return negativeCount;
	}

	/**
	 * Sets the number of negative ratings
	 * 
	 * @param negativeCount the negativeCount to set
	 */
	public void setNegativeCount(int negativeCount) {
		this.negativeCount = negativeCount;
	}

}
