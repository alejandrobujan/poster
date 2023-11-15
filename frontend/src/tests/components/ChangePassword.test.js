import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';

import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import { userMock } from "../mocks/User.mock";

import renderer from "react-test-renderer";

import { ChangePassword } from "../../modules/users";

describe("ChangePassword", () => {
	const middlewares = [thunk];
    const mockStore = configureMockStore(middlewares);
    let store;    
    store = mockStore(userMock);
    
    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<ChangePassword/>
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
                    <ChangePassword />
				</MemoryRouter>
			</Provider>
		);
		
		const oldPassword = screen.getByTestId('OldPassword');
		const newPassword = screen.getByTestId('NewPassword');
		const confirm = screen.getByTestId('Confirm');
		const saveButton = screen.getByTestId('SaveButton');
	});
	
	it("click save button", async () => {
        render(
			<Provider store={store}>
				<MemoryRouter>
                    <ChangePassword />
				</MemoryRouter>
			</Provider>
		);
		
		const saveButton = screen.getByTestId('SaveButton');

        fireEvent.click(saveButton);		
	});
    
});