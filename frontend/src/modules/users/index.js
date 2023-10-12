import * as actions from './actions';
import * as actionTypes from './actionTypes';
import reducer from './reducer';
import * as selectors from './selectors';

export {default as Login} from './components/Login';
export {default as SignUp} from './components/SignUp';
export {default as Logout} from './components/Logout';
export {default as UpdateProfile} from './components/UpdateProfile';

// eslint-disable-next-line
export default {actions, actionTypes, reducer, selectors};