package es.udc.fi.dc.fd.rest.dtos;

import static es.udc.fi.dc.fd.rest.dtos.CategoryConversor.toCategoryDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import es.udc.fi.dc.fd.model.entities.Image;
import es.udc.fi.dc.fd.model.entities.Post;

/**
 * The class PostConversor.
 */
public abstract class PostConversor {

	/**
	 * To image dto.
	 * 
	 * @param image the image
	 * @return the image dto
	 */
	public final static byte[] toImageDto(Image image) {
		return image.getData();
	}

	/**
	 * To image dtos.
	 * 
	 * @param image the image
	 * @return list of imagesDto
	 */
	public final static List<byte[]> toImageDtos(Set<Image> images) {
		return images.stream().map(i -> toImageDto(i)).collect(Collectors.toList());
	}

	/**
	 * Reduce the description of the post
	 * 
	 * @param description the description of the post
	 * @return the shortened description
	 */
	public final static String reduceDescription(String description) {
		return (description.length() > 50 ? description.substring(0, 50) + "..." : description);
	}

	/**
	 * To post summary dto.
	 * 
	 * @param post the post
	 * @return the post summary dto
	 */
	public final static PostSummaryDto toPostSummaryDto(Post post) {
		return new PostSummaryDto(post.getId(), post.getTitle(), reduceDescription(post.getDescription()),
				post.getUrl(), post.getPrice(), toCategoryDto(post.getCategory()), toImageDtos(post.getImages()),
				post.getClass().getSimpleName());
	}

	/**
	 * To post summary dtos
	 * 
	 * @param posts the list of posts
	 * @return the list of post dummary dtos
	 */
	public final static List<PostSummaryDto> toPostSummaryDtos(List<Post> posts) {
		return posts.stream().map(p -> toPostSummaryDto(p)).collect(Collectors.toList());
	}

	/**
	 * Conversor to millis
	 * 
	 * @param date the local date time
	 * @return long that represent the local date time
	 */
	protected final static long toMillis(LocalDateTime date) {
		return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * To post dto.
	 * 
	 * @param post the post
	 * @return the post dto
	 */
	public abstract PostDto toPostDto(Post post);

}
