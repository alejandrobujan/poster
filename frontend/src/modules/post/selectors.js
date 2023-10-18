const getModuleState = state => state.post;

export const getPost = state =>
    getModuleState(state).post;    