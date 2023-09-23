import {useSelector} from 'react-redux';
import Posts from './Posts';
import * as selectors from '../selectors';

const Feed = () => {
    const posts = useSelector(selectors.getPosts);

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