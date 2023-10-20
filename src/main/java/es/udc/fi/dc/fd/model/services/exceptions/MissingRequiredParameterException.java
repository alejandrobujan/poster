package es.udc.fi.dc.fd.model.services.exceptions;

/**
 * The Class MissingRequiredParameterException.
 */
@SuppressWarnings("serial")
public class MissingRequiredParameterException extends Exception {

	/** The parameter that is missing */
	private String parameter;
	/** The post type */
	private String type;

	/**
	 * Instantiates a new MissingRequiredParameterException.
	 * 
	 * @param parameter the parameter that is missing
	 * @param type      the post type
	 */
	public MissingRequiredParameterException(String parameter, String type) {
		this.parameter = parameter;
		this.type = type;
	}

	/**
	 * Gets the parameter
	 * 
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Gets the post type
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
