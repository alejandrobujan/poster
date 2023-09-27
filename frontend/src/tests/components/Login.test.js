import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../store";
import { Login, Logout, SignUp } from "../../modules/users";

import {createMemoryHistory} from 'history';

describe("Login", () => {

    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<Login/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

});