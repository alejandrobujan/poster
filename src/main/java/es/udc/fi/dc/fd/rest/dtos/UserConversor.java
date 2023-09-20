package es.udc.fi.dc.fd.rest.dtos;

import es.udc.fi.dc.fd.model.entities.User;

/**
 * The Class UserConversor.
 */
public class UserConversor {

	/**
	 * Instantiates a new user conversor.
	 */
	private UserConversor() {
	}

	/**
	 * To user dto.
	 *
	 * @param user the user
	 * @return the user dto
	 */
	public static final UserDto toUserDto(User user) {
		return new UserDto(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getRole().toString());
	}

	/**
	 * To user.
	 *
	 * @param userDto the user dto
	 * @return the user
	 */
	public static final User toUser(UserDto userDto) {

		return new User(userDto.getUserName(), userDto.getPassword(), userDto.getFirstName(), userDto.getLastName(),
				userDto.getEmail());
	}

	/**
	 * To authenticated user dto.
	 *
	 * @param serviceToken the service token
	 * @param user         the user
	 * @return the authenticated user dto
	 */
	public static final AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {

		return new AuthenticatedUserDto(serviceToken, toUserDto(user));

	}

}

