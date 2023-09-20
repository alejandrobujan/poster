/**
 * 
 */
package es.udc.fi.dc.fd.rest.dtos;

import es.udc.fi.dc.fd.model.entities.Post;

/**
 * The class PostConversor.
 */
public class PostConversor {
	
	/**
	 * Instantiates a new post conversor.
	 */
	private PostConversor () {
	}
	
	public static final PostParamsDto toPostParamsDto (Post post) {
		return new PostParamsDto(post.getId(), post.getTitle(), post.getDescription(),
				post.getUrl(), post.getPrice(), post.getUser(), post.getCategory(), post.getImages());
	}
	
	public static final Post toPost (PostParamsDto postParamsDto) {
		return new Post(postParamsDto.getId(), postParamsDto.getTitle(), postParamsDto.getDescription(),
				postParamsDto.getUrl(), postParamsDto.getPrice(), postParamsDto.getUser(),
				postParamsDto.getCategory(), postParamsDto.getImages());
	}
	
}
