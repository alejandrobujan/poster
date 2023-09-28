import React from 'react';
import ReactDOM from 'react-dom/client';

import { Provider } from 'react-redux';

import { HashRouter } from 'react-router-dom';

import store from './store';

import backend from './backend';

import { NetworkError } from './backend';

import { App } from "./modules/app";
import app from './modules/app';

import './index.css';

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap';



import registerServiceWorker from './registerServiceWorker';


backend.init(error => store.dispatch(app.actions.error(new NetworkError())));

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
	<React.StrictMode>
		<Provider store={store}>
			<HashRouter>
				<App />
			</HashRouter>
		</Provider>
	</React.StrictMode>
);

registerServiceWorker();
