import {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';

import * as actions from '../actions';

const FindPosts = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {

        dispatch(actions.findPosts({page: 0}));
        navigate('/post/find-posts-result');

    });

    return null;

}

export default FindPosts;