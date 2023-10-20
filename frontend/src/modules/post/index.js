import * as actions from './actions';
import reducer from './reducer';
import * as selectors from './selectors';

export {default as CreatePost} from './components/CreatePost';
export {default as PostDetails} from './components/PostDetails';
export {default as UpdatePost} from './components/UpdatePost';


// eslint-disable-next-line
export default  {actions, reducer, selectors};