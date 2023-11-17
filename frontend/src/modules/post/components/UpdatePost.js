import {useEffect} from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';

import * as selectors from '../selectors';
import * as actions from '../actions';

import UpdatePostForm from './UpdatePostForm';

const UpdatePost = ({ min }) => {
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
    
    return(
		<div>
			<UpdatePostForm min={min}/>
		</div>
	);
};

export default UpdatePost;