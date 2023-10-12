package es.udc.fi.dc.fd.model.services.exceptions;

@SuppressWarnings("serial")
public class MaximumImageSizeExceededException extends Exception {
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
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}
	
}
