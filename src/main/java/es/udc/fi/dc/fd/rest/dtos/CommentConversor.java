package es.udc.fi.dc.fd.rest.dtos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import es.udc.fi.dc.fd.model.entities.Comment;

/**
 * The class CommentConversor.
 */
public class CommentConversor {

	/**
	 * Instantiates a new comment conversor.
	 */
	public CommentConversor() {
	}

	/**
	 * Convert localDateTime to millis
	 * 
	 * @param date the date
	 * @return the date in long format
	 */
	private final static long toMillis(LocalDateTime date) {
		return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * Convert list of comment to list of commentDto
	 * 
	 * @param comments the list of comments
	 * @return the list of commentdto
	 */
	public final static List<CommentDto> toCommentDtos(List<Comment> comments) {
		return comments.stream().map(c -> toCommentDto(c)).collect(Collectors.toList());
	}

	/**
	 * Convert comment to commentdto
	 * 
	 * @param comment the comment
	 * @return the comment dto
	 */
	public static final CommentDto toCommentDto(Comment comment) {
		return new CommentDto(comment.getId(), comment.getDescription(), toMillis(comment.getDate()),
				comment.getAnswers(), comment.getComment() == null ? null : comment.getId(), comment.getLevel(),
				new UserSummaryDto(comment.getUser().getId(), comment.getUser().getUserName(),
						comment.getUser().getFirstName(), comment.getUser().getLastName(),
						comment.getUser().getAvatar()));
	}

}
