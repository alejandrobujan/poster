package es.udc.fi.dc.fd.model.services.exceptions;

@SuppressWarnings("serial")
public class MissingRequiredParameterException extends Exception {
	private String parameter;
	private String type;

	/**
	 * Instantiates a new MaximumImageSizeExceededException.
	 * 
	 * @param parameter
	 * @param type
	 */
	public MissingRequiredParameterException(String parameter, String type) {
		this.parameter = parameter;
		this.type = type;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
