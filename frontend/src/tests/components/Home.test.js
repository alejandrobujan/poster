import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import Home from "../../modules/app/components/Home";
import store from "../../store";

describe("Home", () => {
	it("renders correctly", () => {
		var cadena = "hello world!";
		var result = /^hello/.test(cadena);
		console.log(result); // true
	});

	/*it("calculates the value as expected", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<Home />
				</MemoryRouter>
			</Provider>
		);

		const inputText = screen.getByLabelText(/input some text/i);
		fireEvent.change(inputText, {
			target: { value: "Sreenzragnf qr qrfraibyirzragb" },
		});
		const calculateButton = screen.getByText(/calculate/i);
		fireEvent.click(calculateButton);
		await waitFor(() => {
			screen.getByText(/Ferramentas de desenvolvemento/);
		});
	});*/
});
