import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../../store";
import { SignUp } from "../../../modules/users";
import '@testing-library/jest-dom'

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

	it("registers correctly", async () => {

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

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

	});

	it("registers incorrectly fields required", async () => {

		render(
			<Provider store={store}>
				<MemoryRouter>
					<SignUp/>
				</MemoryRouter>
			</Provider>
		);

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

		expect(screen.getByText(/the login is required/i));
		expect(screen.getByText(/the email is required/i));
		expect(screen.getByText(/the password is required/i));
		expect(screen.getByText(/the first name is required/i));
		expect(screen.getByText(/the last name is required/i));

	});

	it("registers incorrectly password confirmation", async () => {

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
			target: { value: "Pepe@pepe.com" },
		});

        const password = screen.getByLabelText('Password');

        fireEvent.change(password, {
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

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

		expect(screen.getByText(/the password confirmation is required/i));
		

		const confirmPassword = screen.getByLabelText('Confirm password');

        fireEvent.change(confirmPassword, {
			target: { value: "Pepa" },
		});

		fireEvent.click(submit);

		expect(screen.getByText("The passwords don't match"));

	});

});