const getModuleState = state => state.post;

export const getPosts = state =>
    getModuleState(state).posts;

export const getPostSearch = state =>
    getModuleState(state).postSearch;