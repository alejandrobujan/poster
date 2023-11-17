import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import post from '../modules/post';
import catalog from '../modules/catalog';
import notification from '../modules/notification';
import comment from '../modules/comment';

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    post: post.reducer,
    catalog: catalog.reducer,
    notification: notification.reducer,
    comment: comment.reducer
});

export default rootReducer;