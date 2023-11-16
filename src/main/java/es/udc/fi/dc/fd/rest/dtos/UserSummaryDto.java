package es.udc.fi.dc.fd.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
}