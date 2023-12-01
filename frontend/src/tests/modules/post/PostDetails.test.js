import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import { Provider } from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'

import renderer from "react-test-renderer";
import { postMock } from "../../state/Post.mock";

import { PostDetails } from "../../../modules/post";

describe("PostDetails", () => {
	const middlewares = [thunk];
	const mockStore = configureMockStore(middlewares);
	let store;
	store = mockStore(postMock);

	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<MemoryRouter>
						<PostDetails />
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
					<PostDetails />
				</MemoryRouter>
			</Provider>
		);

		const commentForm = screen.getByTestId('CommentForm');
		const comments = screen.getByTestId('Comments');
	});

	it("create comment", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<PostDetails />
				</MemoryRouter>
			</Provider>
		);

		const inputComment = screen.getByTestId('CommentInput');

		fireEvent.change(inputComment, {
			target: { value: "Hola, buenos dias" }
		});

		const commentButton = screen.getByTestId('CommentButton');

		fireEvent.click(commentButton);

	});

	it("reply comment empty", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<PostDetails />
				</MemoryRouter>
			</Provider>
		);



		const inputComment = screen.getByTestId('CommentInput');

		fireEvent.change(inputComment, {
			target: { value: "" }
		});

		const commentButton = screen.getByTestId('CommentButton');

		fireEvent.click(commentButton);

		const errorInput = screen.getByTestId('ErrorForm');

	});
	
	it("click mark post as valid", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <PostDetails />
				</MemoryRouter>
			</Provider>
		);

        const markAsValidButton = screen.getByTestId('MarkAsValidButton');

        await waitFor(() => fireEvent.click(markAsValidButton));  
        
        const markedAsValidText = screen.getByTestId('MarkedAsValidText');        
            
	});
	
	it("click save post", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <PostDetails />
				</MemoryRouter>
			</Provider>
		);

        const savePostButton = screen.getByTestId('SavePostButton');

        await waitFor(() => fireEvent.click(savePostButton));     
            
	});




});