import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk';
import { userMock } from "../mocks/User.mock";

import renderer from "react-test-renderer";

import { ProfileDetail } from "../../modules/users";

describe("ProfileDetails", () => {
	const middlewares = [thunk];
    const mockStore = configureMockStore(middlewares);
    let store;    
    store = mockStore(userMock);
      

    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<ProfileDetail/>
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
                    <ProfileDetail />
				</MemoryRouter>
			</Provider>
		);
		
		const avatar = screen.getByTestId('Avatar');
		const userName = screen.getByTestId('UserName');
		const firstName = screen.getByTestId('FirstName');
		const lastName = screen.getByTestId('LastName');
		const email = screen.getByTestId('Email');
		const editButton = screen.getByTestId('EditButton');
		
	});
	
	it("click edit profile button", async () => {
        render(
			<Provider store={store}>
				<MemoryRouter>
                    <ProfileDetail />
				</MemoryRouter>
			</Provider>
		);
		
		const editProfileButton = screen.getByTestId('EditButton');

        fireEvent.click(editProfileButton);		
	});
	
});