package es.udc.fi.dc.fd.rest.dtos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import es.udc.fi.dc.fd.model.entities.Comment;

public class CommentConversor {

	/**
	 * Instantiates a new comment conversor.
	 */
	public CommentConversor() {
	}

	private final static long toMillis(LocalDateTime date) {
		return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
	}

	public static final CommentDto toCommentDto(Comment comment) {
		return new CommentDto(comment.getId(), comment.getDescription(), toMillis(comment.getDate()),
				new UserSummaryDto(comment.getUser().getId(), comment.getUser().getUserName(),
						comment.getUser().getFirstName(), comment.getUser().getLastName(),
						comment.getUser().getAvatar()));
	}

}