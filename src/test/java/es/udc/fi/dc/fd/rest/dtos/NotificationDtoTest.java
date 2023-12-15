package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * The class NotificationDtoTest
 */
public class NotificationDtoTest {

	/** The user summary dto */
	private UserSummaryDto userSummaryDto;

	/** The set up */
	@BeforeEach
	public void setUp() {
		userSummaryDto = new UserSummaryDto(1L, "username", "Sample", "User", new byte[] { 1, 2, 3 });
	}

	/** Test default constructor not null */
	@Test
	public void testDefaultConstructorNotNull() {
		NotificationDto notification = new NotificationDto();
		assertNotNull(notification);
	}

	/** Test parametrized constructor not null */
	@Test
	public void testParametrizedConstructorNotNull() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertNotNull(notification);
	}

	/** Test get notification id */
	@Test
	public void testGetNotificationId() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals(Long.valueOf(1L), notification.getNotificationId());
	}

	/** Test get notification text */
	@Test
	public void testGetNotificationText() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals("Notification Text", notification.getNotificationText());
	}

	/** Test get notification viewed */
	@Test
	public void testIsNotificationViewed() {
		NotificationDto notification = new NotificationDto();
		assertFalse(notification.isNotificationViewed());
	}

	/** Test get notification creation date */
	@Test
	public void testGetNotificationCreationDate() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals(creationDate, notification.getNotificationCreationDate());
	}

	/** Test get post id */
	@Test
	public void testGetPostId() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals(Long.valueOf(2L), notification.getPostId());
	}

	/** Test get notifier user dto */
	@Test
	public void testGetNotifierUserDto() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals(userSummaryDto, notification.getNotifierUserDto());
	}
}
