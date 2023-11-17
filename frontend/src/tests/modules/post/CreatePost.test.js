import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import { Provider } from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'

import renderer from "react-test-renderer";
import { postMock } from "../../state/Post.mock";

import { CreatePost } from "../../../modules/post";

describe("CreatePost", () => {
	const middlewares = [thunk];
	const mockStore = configureMockStore(middlewares);
	let store;
	store = mockStore(postMock);

	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<MemoryRouter>
						<CreatePost min={10}/>
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
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);
		const editTitle = screen.getByTestId('CreatePostTitleLabel');
		const editDescription = screen.getByTestId('CreatePostDescriptionLabel');
		const editUrl = screen.getByTestId('CreatePostUrlLabel');
		const editPrice = screen.getByTestId('CreatePostPriceLabel');
		const editCategory = screen.getByTestId('CreatePostCategoryLabel');
		const editImages = screen.getByTestId('CreatePostImagesLabel');
	});

	it("edit Title", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputTitle = screen.getByTestId('CreatePostTitleInput');

		fireEvent.change(inputTitle, {
			target: { value: "Rolex" }
		});

		const saveButton = screen.getByTestId('CreatePostSubmitButton');

		fireEvent.click(saveButton);

	});

	it("edit Description", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputDescription = screen.getByTestId('CreatePostDescriptionInput');

		fireEvent.change(inputDescription, {
			target: { value: "En buen estado" }
		});

		const saveButton = screen.getByTestId('CreatePostSubmitButton');

		fireEvent.click(saveButton);

	});

	it("edit Url", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputUrl = screen.getByTestId('CreatePostUrlInput');

		fireEvent.change(inputUrl, {
			target: { value: "https://wwww.rolex.com" }
		});

		const saveButton = screen.getByTestId('CreatePostSubmitButton');

		fireEvent.click(saveButton);

	});

	it("edit Price", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputPrice = screen.getByTestId('CreatePostPriceInput');

		fireEvent.change(inputPrice, {
			target: { value: "15" }
		});

		const saveButton = screen.getByTestId('CreatePostSubmitButton');

		fireEvent.click(saveButton);

	});

	it("edit Category", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputCategory = screen.getByTestId('CreatePostCategoryInput');

		fireEvent.change(inputCategory, {
			target: { value: "meals" }
		});

		const saveButton = screen.getByTestId('CreatePostSubmitButton');

		fireEvent.click(saveButton);

	});

	it("edit Images", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputImages = screen.getByTestId('CreatePostImagesInput');

		fireEvent.change(inputImages, {
			target: { value: "" }
		});

		const saveButton = screen.getByTestId('CreatePostSubmitButton');

		fireEvent.click(saveButton);

	});

	it("click on offer", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const saveButton = screen.getByTestId('CreatePostOfferSelectButton');

		fireEvent.click(saveButton);

	});

	it("click on coupon", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const saveButton = screen.getByTestId('CreatePostCouponSelectButton');

		fireEvent.click(saveButton);
	});

	it("try to post correctly", async () => {

		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const title = screen.getByLabelText(/Title/i);

        fireEvent.change(title, {
			target: { value: "Porsche cayenne" },
		});

		const description = screen.getByLabelText(/Description/i);

        fireEvent.change(description, {
			target: { value: "The ideal car for you. Perfect for everything and for everyone." },
		});

		const url = screen.getByLabelText(/Url/i);

        fireEvent.change(url, {
			target: { value: "https://www.porsche.com/international/models/cayenne/cayenne-models/cayenne/" },
		});

		const price = screen.getByLabelText(/Price/i);

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
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const title = screen.getByLabelText(/Title/i);

        fireEvent.change(title, {
			target: { value: "Porsche cayenne" },
		});

		const description = screen.getByLabelText(/Description/i);

        fireEvent.change(description, {
			target: { value: "The ideal car for you. Perfect for everything and for everyone." },
		});

		const url = screen.getByLabelText(/Url/i);

        fireEvent.change(url, {
			target: { value: "https://www.porsche.com/international/models/cayenne/cayenne-models/cayenne/" },
		});

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

		expect(screen.getByText(/Price must be between 0 and 999999.99/i));

		const price = screen.getByLabelText(/Price/i);

        fireEvent.change(price, {
			target: { value: "55000000.05" },
		});

        fireEvent.click(submit);

		expect(screen.getByText(/Price must be between 0 and 999999.99/i))

	});

	it("try to post incorrect title and description", async () => {

		render(
			<Provider store={store}>
				<MemoryRouter>
					<CreatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const url = screen.getByLabelText(/Url/i);

        fireEvent.change(url, {
			target: { value: "https://www.porsche.com/international/models/cayenne/cayenne-models/cayenne/" },
		});

		const price = screen.getByLabelText(/Price/i);

        fireEvent.change(price, {
			target: { value: "55000.05" },
		});

        const submit = screen.getByRole('button', {name: /submit/i});

        fireEvent.click(submit);

		expect(screen.getByText(/The title size must be between 1 and 60/i));
		expect(screen.getByText(/The description size must be between 1 and 256/i));
	});


});