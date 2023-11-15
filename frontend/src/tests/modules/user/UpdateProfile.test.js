import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk';
import { userMock } from "../../state/User.mock";

import renderer from "react-test-renderer";

import { UpdateProfile } from "../../../modules/users";

describe("UpdateProfile", () => {
	const middlewares = [thunk];
    const mockStore = configureMockStore(middlewares);
    let store;    
    store = mockStore(userMock);
      

    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<UpdateProfile/>
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
                    <UpdateProfile />
				</MemoryRouter>
			</Provider>
		);
		
		const editTitle = screen.getByTestId('EditTitle');
		const login = screen.getByTestId('Login');
		const firstName = screen.getByTestId('FirstName');
		const lastName = screen.getByTestId('LastName');
		const email = screen.getByTestId('Email');
		const avatar = screen.getByTestId('Avatar');
		const preview = screen.getByTestId('Preview');
		const save = screen.getByTestId('SaveButton');
		const changePassword = screen.getByTestId('ChangePasswordButton');
		const clearAvatarButton = screen.getByTestId('ClearAvatarButton');
		
	});

//	it("try write new values in profile () => {
//        render(
//			<Provider store={store}>
//				<MemoryRouter>
//                    <ProfileDetail />
//				</MemoryRouter>
//			</Provider>
//		);
//		
//		const editProfileButton = screen.getByTestId('EditProfileButton');
//
//        fireEvent.click(editProfileButton);		
//	});


	it("click save button", async () => {
        render(
			<Provider store={store}>
				<MemoryRouter>
                    <UpdateProfile />
				</MemoryRouter>
			</Provider>
		);
		
		const saveButton = screen.getByTestId('SaveButton');

        fireEvent.click(saveButton);		
	});
	
	it("click change password button", async () => {
        render(
			<Provider store={store}>
				<MemoryRouter>
                    <UpdateProfile />
				</MemoryRouter>
			</Provider>
		);
		
		const changePasswordButton = screen.getByTestId('ChangePasswordButton');

        fireEvent.click(changePasswordButton);		
	});
	
	it("click clear avatar button", async () => {
        render(
			<Provider store={store}>
				<MemoryRouter>
                    <UpdateProfile />
				</MemoryRouter>
			</Provider>
		);
		
		const clearAvatarButton = screen.getByTestId('ClearAvatarButton');

        fireEvent.click(clearAvatarButton);		
	});
	
	it("click select avatar button", async () => {
        render(
			<Provider store={store}>
				<MemoryRouter>
                    <UpdateProfile />
				</MemoryRouter>
			</Provider>
		);
		
		const selectAvatarButton = screen.getByTestId('SelectAvatarButton');

        fireEvent.click(selectAvatarButton);		
	});
	
});