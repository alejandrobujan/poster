import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';

import * as selectors from '../selectors';
import * as actions from '../actions';
import { BackLink, UserCard } from '../../common';

import ImageGallery from "react-image-gallery";
// import stylesheet if you're not already using CSS @import
import "react-image-gallery/styles/css/image-gallery.css";

const PostDetails = () => {
	const post = useSelector(selectors.getPost);
	const { id } = useParams();
	const dispatch = useDispatch();

	useEffect(() => {

		const postId = Number(id);

		if (!Number.isNaN(postId)) {
			dispatch(actions.findPostById(postId));
		}

		return () => dispatch(actions.clearPost());

	}, [id, dispatch]);

	if (!post) {
		return null;
	}

	return (

		<div>

			<BackLink />

			<div className="card text-center">
				<div className="card-body">
					<h5 className="card-title">{post.title}</h5>
					<p className="card-text">{post.description}</p>
					{post.url &&
						<a className="btn btn-primary" href={post.url} target="_blank">
							<div className="d-flex align-items-center">
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-link" viewBox="0 0 16 16">
									<path d="M6.354 5.5H4a3 3 0 0 0 0 6h3a3 3 0 0 0 2.83-4H9c-.086 0-.17.01-.25.031A2 2 0 0 1 7 10.5H4a2 2 0 1 1 0-4h1.535c.218-.376.495-.714.82-1z"></path>
									<path d="M9 5.5a3 3 0 0 0-2.83 4h1.098A2 2 0 0 1 9 6.5h3a2 2 0 1 1 0 4h-1.535a4.02 4.02 0 0 1-.82 1H12a3 3 0 1 0 0-6H9z"></path>
								</svg>
							</div>
						</a>
					}
					<p className="card-text">{post.price}</p>
					<div className="border-top pt-4">
						{post.images.length === 0 ?
							(<ImageGallery showFullscreenButton={false} showPlayButton={false} showNav={false}
								items={[{ original: '/poster/assets/noimage.png', originalHeight: "300px" }]} />) :
							(<ImageGallery showPlayButton={false}
								items={post.images.map((image) => ({ original: `data:image/*;base64,${image}`, originalHeight: "300px" }))} />)}
					</div>
					{ post.categoryDto && 
						<p className="card-text">{post.categoryDto.name}</p>
					}
					<UserCard user={post.userSummaryDto} />
					<p className="card-text">{post.creationDate}</p>
					<p className="card-text">{post.positiveRatings}</p>
					<p className="card-text">{post.negativeRatings}</p>
					<p className="card-text">{post.expired}</p>
					<p className="card-text">{post.type}</p>
				</div>
			</div>
		</div>

	);
}

export default PostDetails;