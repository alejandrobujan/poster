import { BrowserRouter as Router } from 'react-router-dom';

export const createRouterWrapper = (history) => ({ children }) => (
    <Router history={history}>{children}</Router>
  );