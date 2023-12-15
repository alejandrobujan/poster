const getModuleState = state => state.catalog;

export const getPostSearch = state =>
    getModuleState(state).postSearch;

export const getCategories = state => 
    getModuleState(state).categories;
    
export const getSearchParams = state => 
    getModuleState(state).searchParams;

export const getRequestRefresh = state =>
    getModuleState(state).requestRefresh;
