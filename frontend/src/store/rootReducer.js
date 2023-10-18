import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import post from '../modules/post';
import catalog from '../modules/catalog';

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    post: post.reducer,
    catalog: catalog.reducer
});

export default rootReducer;