import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../store";
import {CreatePost} from "../../modules/post";
import '@testing-library/jest-dom'

import {createMemoryHistory} from 'history';

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


		const history = createMemoryHistory();

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

});