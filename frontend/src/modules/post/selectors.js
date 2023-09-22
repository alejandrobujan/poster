const getModuleState = state => state.post;

export const getPostSearch = state =>
    getModuleState(state).postSearch;