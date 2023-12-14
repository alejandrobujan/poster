import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import { Provider } from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'

import renderer from "react-test-renderer";
import { updatePostMock } from "../../state/UpdatePost.mock";
import { updatePostSecondConditionMock } from "../../state/UpdatePostSecondCondition.mock";

import { UpdatePost } from "../../../modules/post";

describe("UpdatePostFormSecondCondition", () => {
	jest.mock('react-router-dom', () => ({
		...jest.requireActual('react-router-dom'), // use actual for all non-hook parts
		useParams: () => ({ id: '1' }),
	  }));

	  const middlewares = [thunk];
	  const mockStore = configureMockStore(middlewares);
	  let store;
	  store = mockStore(updatePostSecondConditionMock);  

	  it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<MemoryRouter>
						<UpdatePost min={10}/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});
}),

describe("UpdatePostForm", () => {
	const middlewares = [thunk];
	const mockStore = configureMockStore(middlewares);
	let store;
	store = mockStore(updatePostMock);

	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<MemoryRouter>
						<UpdatePost min={10}/>
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
					<UpdatePost />
				</MemoryRouter>
			</Provider>
		);
		const editTitle = screen.getByTestId('UpdatePostFormTitleLabel');
		const editDescription = screen.getByTestId('UpdatePostFormDescriptionLabel');
		const editUrl = screen.getByTestId('UpdatePostFormUrlLabel');
		const editPrice = screen.getByTestId('UpdatePostFormPriceLabel');
		const editCategory = screen.getByTestId('UpdatePostFormCategoryLabel');
		const editImages = screen.getByTestId('UpdatePostFormImagesLabel');
		const editCode = screen.getByTestId('UpdatePostFormCodeLabel');
	});

	it("edit Title", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<UpdatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputTitle = screen.getByTestId('UpdatePostFormTitleInput');

		fireEvent.change(inputTitle, {
			target: { value: "Rolex" }
		});

		const saveButton = screen.getByTestId('UpdatePostFormSaveButton');

		fireEvent.click(saveButton);

	});

	it("edit Code", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<UpdatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputCode = screen.getByTestId('UpdatePostFormCodeInput');

		fireEvent.change(inputCode, {
			target: { value: "REBAJA" }
		});

		const saveButton = screen.getByTestId('UpdatePostFormSaveButton');

		fireEvent.click(saveButton);

	});

	it("edit Description", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<UpdatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputDescription = screen.getByTestId('UpdatePostFormDescriptionInput');

		fireEvent.change(inputDescription, {
			target: { value: "En buen estado" }
		});

		const saveButton = screen.getByTestId('UpdatePostFormSaveButton');

		fireEvent.click(saveButton);

	});

	it("edit Url", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<UpdatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputUrl = screen.getByTestId('UpdatePostFormUrlInput');

		fireEvent.change(inputUrl, {
			target: { value: "https://wwww.rolex.com" }
		});

		const saveButton = screen.getByTestId('UpdatePostFormSaveButton');

		fireEvent.click(saveButton);

	});

	it("edit Price", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<UpdatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputPrice = screen.getByTestId('UpdatePostFormPriceInput');

		fireEvent.change(inputPrice, {
			target: { value: "15" }
		});

		const saveButton = screen.getByTestId('UpdatePostFormSaveButton');

		fireEvent.click(saveButton);

	});

	it("edit Category", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<UpdatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputCategory = screen.getByTestId('UpdatePostFormCategoryInput');

		fireEvent.change(inputCategory, {
			target: { value: "meals" }
		});

		const saveButton = screen.getByTestId('UpdatePostFormSaveButton');

		fireEvent.click(saveButton);

	});

	it("edit Images", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<UpdatePost min={10}/>
				</MemoryRouter>
			</Provider>
		);

		const inputImages = screen.getByTestId('UpdatePostFormImagesInput');

		fireEvent.change(inputImages, {
			target: { value: "" }
		});

		const saveButton = screen.getByTestId('UpdatePostFormSaveButton');

		fireEvent.click(saveButton);

	});
});