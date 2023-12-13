import {useEffect} from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useNavigate } from 'react-router-dom';

import * as selectors from '../selectors';
import * as actions from '../actions';

import * as userSelectors from '../../users/selectors';

import UpdatePostForm from './UpdatePostForm';

const UpdatePost = ({ min }) => {
	const post = useSelector(selectors.getPost);
	const userId = useSelector(userSelectors.getUserId);
	const { id } = useParams();
	const navigate = useNavigate();
    const dispatch = useDispatch();
    useEffect(() => {

		const postId = Number(id);

		if (!Number.isNaN(postId)) {
			dispatch(actions.findPostById(postId, () => navigate('/not-found')));
		}

		return () => dispatch(actions.clearPost());

	}, [id, dispatch, navigate]);

	if (!post) {
		return null;
	}
    
    return(
		<div>
			{userId === post.userSummaryDto.id ?
				<UpdatePostForm min={min}/>
			:
				navigate('/forbidden')
			}
		</div>
	);
};

export default UpdatePost;