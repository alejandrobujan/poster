const getModuleState = state => state.app;

export const getError = state => getModuleState(state).error;

export const getFirstSearch = state => getModuleState(state).firstSearch;