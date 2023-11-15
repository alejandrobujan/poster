const getModuleState = state => state.comment;

export const getComments = state =>
    getModuleState(state).comments;   