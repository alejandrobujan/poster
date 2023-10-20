package es.udc.fi.dc.fd.model.services.exceptions;

/**
 * The Class MaximumImageSizeExceededException.
 */
@SuppressWarnings("serial")
public class MaximumImageSizeExceededException extends Exception {

	/** the max size of the image */
	private int maxSize;

	/**
	 * Instantiates a new MaximumImageSizeExceededException.
	 * 
	 * @param maxSize
	 */
	public MaximumImageSizeExceededException(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * Gets the max size.
	 * 
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

}
