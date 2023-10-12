package es.udc.fi.dc.fd.rest.dtos;

public class UserSummaryDto {
	/** The id. */
	private Long id;
	
	/** The user name. */
	private String userName;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;

	/** The role. */
	private byte[] avatar;

	/**
	 * @param id
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param avatar
	 */
	public UserSummaryDto(Long id, String userName, String firstName, String lastName, byte[] avatar) {
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.avatar = avatar;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the avatar
	 */
	public byte[] getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	
	
	
	
}