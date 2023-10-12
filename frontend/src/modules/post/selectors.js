const getModuleState = state => state.post;

export const getPostSearch = state =>
    getModuleState(state).postSearch;

export const getCategories = state => 
    getModuleState(state).categories;

export const getPost = state =>
    getModuleState(state).post;    