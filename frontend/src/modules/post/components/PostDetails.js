import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';

import * as selectors from '../selectors';
import * as actions from '../actions';
import { BackLink, UserCard } from '../../common';
import { getDate } from '../../../backend/utils';

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

		<div className="container mt-5">
			<BackLink/>
			<div className="row">
				<div className="col-lg-6 text-center">
					{post.images.length === 0 ?
						(<ImageGallery showFullscreenButton={false} showPlayButton={false} showNav={false}
							items={[{ original: '/poster/assets/noimage.png', originalHeight: "300px" }]} />) :
						(<ImageGallery showPlayButton={false}
							items={post.images.map((image) => ({ original: `data:image/*;base64,${image}`, originalHeight: "300px" }))} />)}
				</div>
				<div className="col-lg-6 text-left">
					<h5 className="card-title">{post.title}</h5>
					<p className="card-text">{post.description}</p>
					{post.url &&
						<a className="btn btn-primary mb-3" href={post.url} target="_blank">
							<div className="d-flex align-items-center">
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-link" viewBox="0 0 16 16">
									<path d="M6.354 5.5H4a3 3 0 0 0 0 6h3a3 3 0 0 0 2.83-4H9c-.086 0-.17.01-.25.031A2 2 0 0 1 7 10.5H4a2 2 0 1 1 0-4h1.535c.218-.376.495-.714.82-1z"></path>
									<path d="M9 5.5a3 3 0 0 0-2.83 4h1.098A2 2 0 0 1 9 6.5h3a2 2 0 1 1 0 4h-1.535a4.02 4.02 0 0 1-.82 1H12a3 3 0 1 0 0-6H9z"></path>
								</svg>
								<span className="ml-2">Ver enlace</span>
							</div>
						</a>
					}
					<p className="card-text"><strong>Price:</strong> {post.price}</p>
					{post.categoryDto &&
						<p className="card-text"><strong>Category:</strong> {post.categoryDto.name}</p>
					}
					<UserCard user={post.userSummaryDto} />
					<p className="card-text"><strong>Creation date:</strong> {getDate(post.creationDate)}</p>
					<button type="button" class="btn btn-secondary">
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-hand-thumbs-up-fill" viewBox="0 0 16 16">
							<path d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"></path>
						</svg>
						&nbsp;
						{post.positiveRatings}
					</button>
					&nbsp;
					&nbsp;
					<button type="button" class="btn btn-secondary">
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-hand-thumbs-down-fill" viewBox="0 0 16 16">
							<path d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"></path>
						</svg>
						&nbsp;
						{post.negativeRatings}
					</button>
					<p className="card-text"><strong>Expired:</strong> {post.expired ? 'Yes' : 'No'}</p>
					<p className="card-text"><strong>Type:</strong> {post.type}</p>
				</div>
			</div>
		</div>

	);
}


export default PostDetails;