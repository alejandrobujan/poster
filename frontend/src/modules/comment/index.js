import * as actions from './actions';
import reducer from './reducer';
import * as selectors from './selectors';

export {default as Comments} from './components/Comments';
export {default as Comment} from './components/Comment';

// eslint-disable-next-line
export default  {actions, reducer, selectors};