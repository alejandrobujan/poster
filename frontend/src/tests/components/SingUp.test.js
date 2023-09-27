import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../store";
import { SignUp } from "../../modules/users";
import { App } from "../../modules/app";

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

    it("do sign up", async () => {
        const history = createMemoryHistory();

		render(
			<Provider store={store}>
				<MemoryRouter>
					<App/>
				</MemoryRouter>
			</Provider>
		);

		const loginLink = screen.getByText('Login');

		fireEvent.click(loginLink);
 
        await waitFor(() => {
            const signUp = screen.getByText(/Sign up/);
			fireEvent.click(signUp);
        }).then(async () => {
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

	
		})/*.then(async () => {
			await waitFor(() => {
				screen.getByText(/No posts found/i);
			});
		});

        /*const login = screen.getByLabelText('Login');

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

		await waitFor(() => {
			expect(history.location.pathname).toBe('/poster');
		});

        //expect(history.location.pathname).toEqual('/poster');*/



	});

    
    /*it("do sign up wrong", async () => {
        const history = createMemoryHistory();

		render(
			<Provider store={store}>
				<MemoryRouter>
					<SignUp/>
				</MemoryRouter>
			</Provider>
		);

        const login = screen.getByLabelText('Login');

        fireEvent.change(login, {
			target: { value: "Pepe" },
		});

        const email = screen.getByLabelText('Email');

        fireEvent.change(email, {
			target: { value: "Pepe" },
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

        expect(history.location.pathname).toEqual('/poster/users/signup');

        const alert = await screen.findByRole('alert');

	});*/
});