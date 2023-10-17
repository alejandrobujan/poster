package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;

public class RatePostDto {

	@NotNull
	private int positiveCount;

	@NotNull
	private int negativeCount;

	/**
	 * @param positiveCount
	 * @param negativeCount
	 */
	public RatePostDto(int positiveCount, int negativeCount) {
		this.positiveCount = positiveCount;
		this.negativeCount = negativeCount;
	}

	/**
	 * @return the positiveCount
	 */
	public int getPositiveCount() {
		return positiveCount;
	}

	/**
	 * @param positiveCount the positiveCount to set
	 */
	public void setPositiveCount(int positiveCount) {
		this.positiveCount = positiveCount;
	}

	/**
	 * @return the negativeCount
	 */
	public int getNegativeCount() {
		return negativeCount;
	}

	/**
	 * @param negativeCount the negativeCount to set
	 */
	public void setNegativeCount(int negativeCount) {
		this.negativeCount = negativeCount;
	}

}
