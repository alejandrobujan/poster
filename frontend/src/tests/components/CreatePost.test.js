import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../store";
import {CreatePost} from "../../modules/post";
import '@testing-library/jest-dom'

describe("CreatePost", () => {

    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<CreatePost/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

	it("try to post correctly", async () => {

		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost/>
				</MemoryRouter>
			</Provider>
		);

		const title = screen.getByLabelText('Title');

        fireEvent.change(title, {
			target: { value: "Porsche cayenne" },
		});

		const description = screen.getByLabelText('Description');

        fireEvent.change(description, {
			target: { value: "The ideal car for you. Perfect for everything and for everyone." },
		});

		const url = screen.getByLabelText('Url');

        fireEvent.change(url, {
			target: { value: "https://www.porsche.com/international/models/cayenne/cayenne-models/cayenne/" },
		});

		const price = screen.getByLabelText('Price');

        fireEvent.change(price, {
			target: { value: "55000.05" },
		});

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

	});

	it("try to post incorrect quantity", async () => {

		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost/>
				</MemoryRouter>
			</Provider>
		);

		const title = screen.getByLabelText('Title');

        fireEvent.change(title, {
			target: { value: "Porsche cayenne" },
		});

		const description = screen.getByLabelText('Description');

        fireEvent.change(description, {
			target: { value: "The ideal car for you. Perfect for everything and for everyone." },
		});

		const url = screen.getByLabelText('Url');

        fireEvent.change(url, {
			target: { value: "https://www.porsche.com/international/models/cayenne/cayenne-models/cayenne/" },
		});

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

		expect(screen.getByText(/Price must be between 0 and 9999999.99/i));

		const price = screen.getByLabelText('Price');

        fireEvent.change(price, {
			target: { value: "55000000.05" },
		});

        fireEvent.click(submit);

		expect(screen.getByText(/Price must be between 0 and 9999999.99/i))

	});

	it("try to post incorrect title and description", async () => {

		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost/>
				</MemoryRouter>
			</Provider>
		);

		const url = screen.getByLabelText('Url');

        fireEvent.change(url, {
			target: { value: "https://www.porsche.com/international/models/cayenne/cayenne-models/cayenne/" },
		});

		const price = screen.getByLabelText('Price');

        fireEvent.change(price, {
			target: { value: "55000.05" },
		});

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

		expect(screen.getByText(/The title size must be between 1 and 60/i));
		expect(screen.getByText(/The description size must be between 1 and 256/i));
	});


});