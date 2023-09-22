import * as actions from './actions';
import reducer from './reducer';
import * as selectors from './selectors';

export {default as CreatePost} from './components/CreatePost';
export {default as FindPosts} from './components/FindPosts';
export {default as FindPostsResult} from './components/FindPostsResult';
export {default as Post} from './components/Post';
export {default as Posts} from './components/Posts';


// eslint-disable-next-line
export default  {actions, reducer, selectors};