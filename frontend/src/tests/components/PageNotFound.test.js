import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import { Provider } from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk';
import { userMock } from "../state/User.mock";

import renderer from "react-test-renderer";

import PageNotFound from '../../modules/common/components/PageNotFound';

describe('PageNotFound Component', () => {
	const middlewares = [thunk];
	const mockStore = configureMockStore(middlewares);
	let store;
	store = mockStore(userMock);


	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<MemoryRouter>
						<PageNotFound />
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

	it("basic structure", () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<PageNotFound />
				</MemoryRouter>
			</Provider>
		);

		const title = screen.getByTestId('Title');
		const description = screen.getByTestId('Description');

	});
});