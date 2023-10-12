/**
 * 
 */
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

	public final static byte[] toImageDto(Image image) {
		return image.getData();
	}

	public final static List<byte[]> toImageDtos(Set<Image> images) {
		return images.stream().map(i -> toImageDto(i)).collect(Collectors.toList());
	}

	public final static PostSummaryDto toPostSummaryDto(Post post) {
		return new PostSummaryDto(post.getId(), post.getTitle(), post.getDescription(), post.getUrl(), post.getPrice(),
				toCategoryDto(post.getCategory()), toImageDtos(post.getImages()));
	}

	public final static List<PostSummaryDto> toPostSummaryDtos(List<Post> posts) {
		return posts.stream().map(p -> toPostSummaryDto(p)).collect(Collectors.toList());
	}

	protected final static long toMillis(LocalDateTime date) {
		return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
	}

	public abstract PostDto toPostDto(Post post);

}
