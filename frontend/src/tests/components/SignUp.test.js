import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../store";
import { SignUp } from "../../modules/users";

import {createMemoryHistory} from 'history';

describe("SignUp", () => {
	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<MemoryRouter>
						<SignUp/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

});