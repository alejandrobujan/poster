import { useSelector } from 'react-redux';
import { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';

import * as userSelectors from '../../users/selectors';
import { getDate } from '../../../backend/utils';
import { Errors } from '../../common';
import * as commentActions from '../../comment/actions';
import { Comments } from "../../comment";

const Comment = ({ id, description, date, answers, parentId, level, user, postId }) => {

	const [commentAnswers, setCommentAnswers] = useState({ items: [], existMoreItems: false });

	const [reply, setReply] = useState(false);
	const [seeAnswers, setSeeAnswers] = useState(false);
	const [comment, setComment] = useState('');
	const [backendErrors, setBackendErrors] = useState(null);
	const [page, setPage] = useState(0);

	const isLoggedIn = useSelector(userSelectors.isLoggedIn);

	const [existAnswers, setExistAnswers] = useState(answers > 0);

	const margin = level * 10;

	const dispatch = useDispatch();

	let form;

	useEffect(() => {
		setExistAnswers(answers > 0);
	  }, [answers]);

	const handleSubmit = event => {

		event.preventDefault();

		if (form.checkValidity()) {
			dispatch(commentActions.commentAnswer(comment, postId, id, 0, comments => {
				setExistAnswers(true);
				setCommentAnswers(comments);
				setSeeAnswers(true);
				setPage(0);
				setComment('');
				setReply(false);
			}, errors => setBackendErrors(errors)))
		} else {
			setBackendErrors(null);
			form.classList.add('was-validated');
		}
	}

	return (
		<div>
			<div className="card mb-4" style={{ marginLeft: `${margin}px` }}>
				<Errors id="createCommentErrors" errors={backendErrors} onClose={() => setBackendErrors(null)} />
				<div className="card-body p-4">
					<div className="d-flex flex-start">
						<div className="flex-grow-1 flex-shrink-1">
							<div>
								<div className="d-flex justify-content-between align-items-center">
									<div className="mb-1">
										<div className="d-flex flex-row mb-2">
											<img className="mr-3"
												src={user.avatar ? `data:image/*;base64,${user.avatar}` : "/poster/assets/profile.png"}
												alt="Avatar"
												width="30px"
												height="30px"
											/> {user.userName}
										</div>
										<div className="d-flex flex-row mb-3">
											<span className="small" style={{ color: 'grey' }}>
												{getDate(date)}
											</span>
										</div>
									</div>
								</div>
								<span className="mb-0">{description}</span>
							</div>
						</div>
						{isLoggedIn && 
							<div role="button" className="color-text mr-3" onClick={() => {setReply(!reply)}}>
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-reply" viewBox="0 0 16 16">
									<path d="M6.598 5.013a.144.144 0 0 1 .202.134V6.3a.5.5 0 0 0 .5.5c.667 0 2.013.005 3.3.822.984.624 1.99 1.76 2.595 3.876-1.02-.983-2.185-1.516-3.205-1.799a8.74 8.74 0 0 0-1.921-.306 7.404 7.404 0 0 0-.798.008h-.013l-.005.001h-.001L7.3 9.9l-.05-.498a.5.5 0 0 0-.45.498v1.153c0 .108-.11.176-.202.134L2.614 8.254a.503.503 0 0 0-.042-.028.147.147 0 0 1 0-.252.499.499 0 0 0 .042-.028l3.984-2.933zM7.8 10.386c.068 0 .143.003.223.006.434.02 1.034.086 1.7.271 1.326.368 2.896 1.202 3.94 3.08a.5.5 0 0 0 .933-.305c-.464-3.71-1.886-5.662-3.46-6.66-1.245-.79-2.527-.942-3.336-.971v-.66a1.144 1.144 0 0 0-1.767-.96l-3.994 2.94a1.147 1.147 0 0 0 0 1.946l3.994 2.94a1.144 1.144 0 0 0 1.767-.96v-.667z"/>
								</svg>
								<small>
									Reply
								</small>
							</div>
						}
					</div>
					{reply &&
						<div>
							<hr className="my-4" />
							<form ref={node => form = node}
								className="needs-validation" noValidate
								onSubmit={e => handleSubmit(e)}>
								<div>
									<input type="text"
										id="commentAnswer"
										className="form-control"
										placeholder="Add a reply"
										value={comment}
										onChange={e => setComment(e.target.value)}
										autoFocus
										minLength={1}
										maxLength={256}
										required />
									<div className="invalid-feedback">
										The comment size must be between 1 and 256
									</div>
								</div>	
								<div className="text-right my-3">
									<button type="submit" className="btn btn-primary">
										Comment
									</button>
								</div>
							</form>
						</div>
					}
					{existAnswers &&
						<div role="button" className="color-text" onClick={() => {
							if (seeAnswers) {
								setPage(0);
							} else {
								dispatch(commentActions.findAnswersComment(postId, id, page, comments => {
									setCommentAnswers(comments)
								}))
							}
							setSeeAnswers(!seeAnswers);
						}}>
							<hr className="my-4" />
							{seeAnswers ?
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-caret-down-fill" viewBox="0 0 16 16">
									<path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z" />
								</svg>
								:
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-caret-right-fill" viewBox="0 0 16 16">
									<path d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z" />
								</svg>
							}
							&nbsp;
							See answers
						</div>
					}
				</div>
			</div>
			{seeAnswers &&
				<div>
					<Comments comments={commentAnswers.items} postId={postId} />
					{commentAnswers.existMoreItems &&
						<button type="button" className="btn btn-secondary mb-4" onClick={() => {
							dispatch(commentActions.nextFindAnswersResultPage(postId, id, page, results => {
								setCommentAnswers({ items: commentAnswers.items.concat(results.items), existMoreItems: results.existMoreItems });
								setPage(page + 1);
							}))
						}}>
							More answers
						</button>
					}
				</div>
			}
		</div>
	);
}

export default Comment;