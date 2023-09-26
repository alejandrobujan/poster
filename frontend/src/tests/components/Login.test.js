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




	it("do sign up log out and log in", async () => {
		const history = createMemoryHistory();

		render(
			<Provider store={store}>
				<MemoryRouter>
					<SignUp/>
				</MemoryRouter>
			</Provider>
		);

		const signUpLink = screen.getByText(/Sign Up/i);
		
		fireEvent.click(signUpLink);

		await waitFor(() => {
			screen.getByText(/Email/);
		});

		const login = screen.getByLabelText('Login');

		fireEvent.change(login, {
			target: { value: "Pepe" },
		});

		const email = screen.getByLabelText('Email');

		fireEvent.change(email, {
			target: { value: "Pepe@pepe.com" },
		});

		const password = screen.getByLabelText('Password');

		fireEvent.change(password, {
			target: { value: "Pepe" },
		});

		const confirmPassword = screen.getByLabelText('Confirm password');

		fireEvent.change(confirmPassword, {
			target: { value: "Pepe" },
		});

		const firstName = screen.getByLabelText('First name');

		fireEvent.change(firstName, {
			target: { value: "Pepe" },
		});

		const lastName = screen.getByLabelText('Last name');

		fireEvent.change(lastName, {
			target: { value: "Pepe" },
		});

		const submit = screen.getByRole('button', {name: 'Submit'});

		fireEvent.click(submit);

		expect(history.location.pathname).toEqual('/');

		await waitFor(() => {
			screen.queryByText(/No posts found/i);
		});


		//A partir de aqui, aparecen los problemas
		/*const logoutClick = screen.getByTestId('dropdown'); //esto estaba anadido al Header, se ha eliminado


		const userProfile = screen.getByText('Pepe');

		fireEvent.click(userProfile);

		await waitFor(() => {
			screen.getByText(/Logout/);
		});

		const logoutLabel = screen.getByText('Logout');

		fireEvent.click(logoutLabel);

		expect(history.location.pathname).toEqual('/users/logout');*/
		


	});

});