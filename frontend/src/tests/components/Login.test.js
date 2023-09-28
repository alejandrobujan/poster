import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../store";
import { Login } from "../../modules/users";

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

	it("try to login", async () => {

		render(
			<Provider store={store}>
				<MemoryRouter>
					<Login/>
				</MemoryRouter>
			</Provider>
		);

		const login = screen.getByLabelText('Login');

        fireEvent.change(login, {
			target: { value: "Pepe" },
		});

		const password = screen.getByLabelText('Password');

        fireEvent.change(password, {
			target: { value: "Pepe" },
		});

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

	});

	it("login incorrectly fields required", async () => {

		render(
			<Provider store={store}>
				<MemoryRouter>
					<Login/>
				</MemoryRouter>
			</Provider>
		);

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

		expect(screen.getByText(/the login is required/i));
		expect(screen.getByText(/the password is required/i));

	});

});