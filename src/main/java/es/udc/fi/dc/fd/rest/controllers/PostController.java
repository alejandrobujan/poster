package es.udc.fi.dc.fd.rest.controllers;

import static es.udc.fi.dc.fd.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.fi.dc.fd.rest.dtos.PostConversor.toPostSummaryDtos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.Block;
import es.udc.fi.dc.fd.model.services.PostService;
import es.udc.fi.dc.fd.rest.dtos.BlockDto;
import es.udc.fi.dc.fd.rest.dtos.CategoryDto;
import es.udc.fi.dc.fd.rest.dtos.PostParamsDto;
import es.udc.fi.dc.fd.rest.dtos.PostSummaryDto;

/**
 * The Class PostController.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
	
    @Autowired
    private PostService postService;
    
    @PostMapping("/post")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createPost(@RequestAttribute Long userId, @Validated @RequestBody PostParamsDto params) 
    		throws InstanceNotFoundException{
    	
    	postService.createPost(params.getTitle(), params.getDescription(), params.getUrl(), 
    			params.getPrice(), userId, params.getCategoryId(), params.getImages());
 
    }
    
	
	@GetMapping("/feed")
    public BlockDto<PostSummaryDto> findAllPosts(@RequestParam(defaultValue="0") int page){
		
		Block<Post> posts = postService.findAllPosts(page, 4);

        return new BlockDto<>(toPostSummaryDtos(posts.getItems()), posts.getExistMoreItems());
        
	}
	
	@GetMapping("/categories")
	public List<CategoryDto> findAllCategories() {
		
		return toCategoryDtos(postService.findAllCategories());
		
		
	}
	
}