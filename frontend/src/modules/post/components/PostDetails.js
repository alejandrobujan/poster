import { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useNavigate } from 'react-router-dom';

import * as selectors from '../selectors';
import * as userSelectors from '../../users/selectors';
import * as actions from '../actions';

import * as commentActions from '../../comment/actions';
import * as commentSelectors from '../../comment/selectors';

import { BackLink, UserCard, Errors } from '../../common';
import { Pager } from '../../common';
import { getDate } from '../../../backend/utils';
import { OfferIcon, CouponIcon } from "../../catalog";
import { Comments } from "../../comment";

import ImageGallery from "react-image-gallery";
// import stylesheet if you're not already using CSS @import
import "react-image-gallery/styles/css/image-gallery.css";

const PostDetails = () => {
	const post = useSelector(selectors.getPost);
	const comments = useSelector(commentSelectors.getComments);
	const user = useSelector(userSelectors.getUser);
	const isLoggedIn = useSelector(userSelectors.isLoggedIn);
	const userName = useSelector(userSelectors.getUserName);
	const { id } = useParams();
	const navigate = useNavigate();
	const dispatch = useDispatch();
	const [comment, setComment] = useState('');
	const [backendErrors, setBackendErrors] = useState(null);
	const [buttonPressed, setButtonPressed] = useState(false);

	let form;

	const components = {
		'Offer': OfferIcon,
		'Coupon': CouponIcon
	};

	const handleSubmit = event => {

		event.preventDefault();

		if (form.checkValidity()) {
			dispatch(commentActions.commentPost(comment, id, null, 0, errors => setBackendErrors(errors)))
			setComment('');
		} else {
			setBackendErrors(null);
			form.classList.add('was-validated');
		}
	}


	const handleDeleteClick = () => {
		if (window.confirm("Are you sure you want to delete this post? This action is irreversible.")) {
			dispatch(actions.deletePost(id, () => navigate("/"), errors => setBackendErrors(errors)));
		}

	}

	useEffect(() => {

		const postId = Number(id);

		if (!Number.isNaN(postId)) {
			dispatch(actions.findPostById(postId));
			dispatch(commentActions.findComments(postId, null, 0));
		}

		return () => {
			dispatch(actions.clearPost())
			dispatch(commentActions.clearComments());
		};

	}, [id, dispatch]);

	if (!post) {
		return null;
	}

	const Icon = components[post.type];

	return (
		<div className="container mt-5">
			<Errors id="createPostErrors" errors={backendErrors} onClose={() => setBackendErrors(null)} />
			{post.expirationDate <= (new Date().getTime()) && <div className="alert alert-dark" role="alert">
				This post is expired... but maybe it can still help you.
			</div>}
			<div className="row">
				<div className="col-lg-6 text-center">
					{post.images.length === 0 ?
						(<ImageGallery showFullscreenButton={false} showPlayButton={false} showNav={false}
							items={[{ original: '/poster/assets/noimage.png', originalHeight: "300px" }]} />) :
						(<ImageGallery showPlayButton={false}
							items={post.images.map((image) => ({ original: `data:image/*;base64,${image}`, thumbnailHeight: "300px" }))} />)}
					<div className="mt-4">
						<BackLink /> &nbsp;
						{post.url &&
							<a className="btn btn-primary" href={`//${post.url}`} target="_blank" rel="noreferrer">
								<div className="d-flex align-items-center">
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-box-arrow-up-right" viewBox="0 0 16 16">
										<path fillRule="evenodd" d="M8.636 3.5a.5.5 0 0 0-.5-.5H1.5A1.5 1.5 0 0 0 0 4.5v10A1.5 1.5 0 0 0 1.5 16h10a1.5 1.5 0 0 0 1.5-1.5V7.864a.5.5 0 0 0-1 0V14.5a.5.5 0 0 1-.5.5h-10a.5.5 0 0 1-.5-.5v-10a.5.5 0 0 1 .5-.5h6.636a.5.5 0 0 0 .5-.5z" />
										<path fillRule="evenodd" d="M16 .5a.5.5 0 0 0-.5-.5h-5a.5.5 0 0 0 0 1h3.793L6.146 9.146a.5.5 0 1 0 .708.708L15 1.707V5.5a.5.5 0 0 0 1 0v-5z" />
									</svg>
									<span className="ml-2">Go to site</span>
								</div>
							</a>
						}
						&nbsp;
						<button type="button" className="btn btn-secondary" onClick={() => isLoggedIn ?
							dispatch(actions.ratePostPositive(id, errors => setBackendErrors(errors)))
							:
							navigate("/users/login")
						}>
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-hand-thumbs-up-fill" viewBox="0 0 16 16">
								<path d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"></path>
							</svg>
							&nbsp;
							{post.positiveRatings}
						</button>
						&nbsp;
						<button type="button" className="btn btn-secondary" onClick={() => isLoggedIn ?
							dispatch(actions.ratePostNegative(id, errors => setBackendErrors(errors)))
							:
							navigate("/users/login")
						}>
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-hand-thumbs-down-fill" viewBox="0 0 16 16">
								<path d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"></path>
							</svg>
							&nbsp;
							{post.negativeRatings}
						</button>
						&nbsp;
						{userName != null && (user.id === post.userSummaryDto.id) &&
							<button className="btn btn-primary" onClick={() => navigate(`/post/post-update/${id}`)}>
								<div className="d-flex align-items-center">
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="24" fill="currentColor" className="bi bi-pencil-square" viewBox="0 0 16 16">
										<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
										<path fillRule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
									</svg>
								</div>
							</button>
						}
						&nbsp;
						{isLoggedIn && post.userSummaryDto.id === user.id &&
							<button type="button" className="btn btn-outline-danger" onClick={handleDeleteClick}>
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash" viewBox="0 0 16 16">
									<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"></path>
									<path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"></path>
								</svg></button>
						}

					</div>

				</div>
				<div className="col-lg-6 text-left">
					<Icon />
					<hr className="my-1" />
					<h5 className="fw-bolder">{post.title}</h5>
					<p className="card-text"><i>{post.description}</i></p>

					<p className="post-price">{post.price.toFixed(2)}€</p>

					{post.categoryDto &&
						<p className="card-text"><strong>Category:</strong> {post.categoryDto.name}</p>
					}

					<p className="card-text"><strong>Expiration:</strong> {getDate(post.expirationDate).substring(0, getDate(post.expirationDate).length - 3)}</p>
					{post.validationDate &&
						<p className="mt-2" data-testid="MarkedAsValidText"><strong>Marked as valid on:</strong> {getDate(post.validationDate).substring(0, getDate(post.validationDate).length - 3)} </p>
					}
					{post.properties.code &&
						<div className="copy-button">
							<input id="copyvalue" type="text" readOnly value={post.properties.code} />
							{!buttonPressed ?
								<button className="btn btn-primary" onClick={() => {
									navigator.clipboard.writeText(post.properties.code);
									setButtonPressed(true);
								}}>
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-copy" viewBox="0 0 16 16">
										<path fillRule="evenodd" d="M4 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V2Zm2-1a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H6ZM2 5a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1v-1h1v1a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h1v1H2Z" />
									</svg>
								</button>
								:
								<button type="button" className="btn btn-secondary">
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-check2" viewBox="0 0 16 16">
										<path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"></path>
									</svg>
								</button>
							}
						</div>
					}
					<div className="post-signature">
						<div>Posted at {getDate(post.creationDate)}</div>
						<UserCard user={post.userSummaryDto} />
					</div>

					<div className="row">
						{
							isLoggedIn && post.userSummaryDto.id === user.id && (post.expirationDate > (new Date().getTime())) &&
							<button className="page-link mt-2 mr-4"
								onClick={() => dispatch(actions.markPostAsExpired(id, errors => setBackendErrors(errors)))}>
								Mark as expired
							</button>

						}
						{isLoggedIn && post.userSummaryDto.id !== user.id &&
							<button className="page-link mt-2" data-testid="MarkAsValidButton"
								onClick={() => dispatch(actions.markPostAsValid(id, errors => setBackendErrors(errors)))}>
								Mark as still valid
							</button>
						}
					</div>
				</div>
			</div>
			<div>
				&nbsp;
				{isLoggedIn &&
					<form data-testid="CommentForm" ref={node => form = node}
						className="needs-validation container ml-1" noValidate
						onSubmit={e => handleSubmit(e)}>
						<div>
							<input data-testid="CommentInput" type="text"
								id="comment"
								className="form-control"
								placeholder="Add a comment"
								value={comment}
								onChange={e => setComment(e.target.value)}
								autoFocus
								minLength={1}
								maxLength={256}
								required />
							<div data-testid="ErrorForm" className="invalid-feedback">
								The comment size must be between 1 and 256
							</div>
						</div>
						<div className="text-right">
							<button data-testid="CommentButton" type="submit" className="btn btn-primary my-3">
								Comment
							</button>
						</div>
					</form>
				}
				<div data-testid="Comments">
					<Comments comments={comments.elems.items} postId={id} />
					<Pager
						back={{
							enabled: comments.page >= 1,
							onClick: () => dispatch(commentActions.previousFindCommentsResultPage(id, null, comments.page))
						}}
						next={{
							enabled: comments.elems.existMoreItems,
							onClick: () => dispatch(commentActions.nextFindCommentsResultPage(id, null, comments.page))
						}} />
				</div >
			</div >
		</div >

	);
}


export default PostDetails;