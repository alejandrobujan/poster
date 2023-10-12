import * as actions from './actions';
import reducer from './reducer';
import * as selectors from './selectors';

export {default as CreatePost} from './components/CreatePost';
export {default as CategorySelector} from './components/CategorySelector';
export {default as Post} from './components/Post';
export {default as Posts} from './components/Posts';
export {default as Feed} from './components/Feed';
export {default as PostDetails} from './components/PostDetails';


// eslint-disable-next-line
export default  {actions, reducer, selectors};