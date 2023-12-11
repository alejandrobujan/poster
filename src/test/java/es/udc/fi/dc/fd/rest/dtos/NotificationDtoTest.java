package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class NotificationDtoTest {

	private UserSummaryDto userSummaryDto;

	@BeforeEach
	public void setUp() {
		userSummaryDto = new UserSummaryDto(1L, "username", "Sample", "User", new byte[] { 1, 2, 3 });
	}

	@Test
	public void testDefaultConstructorNotNull() {
		NotificationDto notification = new NotificationDto();
		assertNotNull(notification);
	}

	@Test
	public void testParametrizedConstructorNotNull() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertNotNull(notification);
	}

	@Test
	public void testGetNotificationId() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals(Long.valueOf(1L), notification.getNotificationId());
	}

	@Test
	public void testGetNotificationText() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals("Notification Text", notification.getNotificationText());
	}

	@Test
	public void testIsNotificationViewed() {
		NotificationDto notification = new NotificationDto();
		assertFalse(notification.isNotificationViewed());
	}

	@Test
	public void testGetNotificationCreationDate() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals(creationDate, notification.getNotificationCreationDate());
	}

	@Test
	public void testGetPostId() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals(Long.valueOf(2L), notification.getPostId());
	}

	@Test
	public void testGetNotifierUserDto() {
		LocalDateTime creationDate = LocalDateTime.now();

		NotificationDto notification = new NotificationDto(1L, "Notification Text", false, creationDate, 2L,
				userSummaryDto);

		assertEquals(userSummaryDto, notification.getNotifierUserDto());
	}
}
