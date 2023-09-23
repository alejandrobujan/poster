import {useSelector, useDispatch} from 'react-redux';
import Posts from './Posts';
import * as selectors from '../selectors';
import * as actions from '../actions';

const Feed = () => {
    const posts = useSelector(selectors.getPosts);
    const dispatch = useDispatch();

    if (!posts) {
        return null;
    }

    return (
        <div>
            <Posts posts={posts}/>
        </div>
    );

}

export default Feed;