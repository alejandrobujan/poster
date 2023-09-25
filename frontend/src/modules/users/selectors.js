const getModuleState = state => state.users;

export const getUser = state => 
    getModuleState(state).user;

export const isLoggedIn = state =>
    getUser(state) !== null

export const getUserName = state => 
    isLoggedIn(state) ? getUser(state).userName : null;
    
export const getAvatar = state => 
    isLoggedIn(state) ? getUser(state).avatar : null;