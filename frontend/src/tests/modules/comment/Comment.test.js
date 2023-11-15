import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'

import renderer from "react-test-renderer";
import { commentMock } from "../state/Comment.mock";

import { Comment } from "../../../modules/comment";

describe("Comment", () => {

	const middlewares = [thunk];
    const mockStore = configureMockStore(middlewares);
    let store;
    store = mockStore(commentMock);

    const user = {
        avatar: null,
        userName: "Pepe"
    }

    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<Comment id={1} description={"Hola"} date={null} answers={2} parentId={2} level={2} user={user} postId={1}/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

    it("basic structure comment", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <Comment id={1} description={"Hola"} date={null} answers={2} parentId={2} level={2} user={user} postId={1}/>
				</MemoryRouter>
			</Provider>
		);

		const userImage = screen.getByTestId('UserImage');

        const commentDate = screen.getByTestId('CommentDate');

        const commentDescription = screen.getByTestId('CommentDescription');

	});

    it("click see answers comment", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <Comment id={1} description={"Hola"} date={null} answers={2} parentId={2} level={2} user={user} postId={1}/>
				</MemoryRouter>
			</Provider>
		);

        const seeAnswersButton = screen.getByTestId('SeeAnswersButton');

        fireEvent.click(seeAnswersButton);

        const seeAnswers = screen.getByTestId('SeeAnswers');

	});

	it("click reply", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <Comment id={1} description={"Hola"} date={null} answers={2} parentId={2} level={2} user={user} postId={1}/>
				</MemoryRouter>
			</Provider>
		);

        const replyButton = screen.getByTestId('ReplyButton');

        fireEvent.click(replyButton);

        const replyForm = screen.getByTestId('ReplyForm');

	});

	it("reply comment", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <Comment id={1} description={"Hola"} date={null} answers={2} parentId={2} level={2} user={user} postId={1}/>
				</MemoryRouter>
			</Provider>
		);

        const replyButton = screen.getByTestId('ReplyButton');

        fireEvent.click(replyButton);

        const inputComment = screen.getByTestId('InputComment');

		fireEvent.change(inputComment, {
			target: {value: "Hola, buenos dias"}
		});

		const commentButton = screen.getByTestId('CommentButton');

		fireEvent.click(commentButton);

	});

	it("reply comment empty", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <Comment id={1} description={"Hola"} date={null} answers={2} parentId={2} level={2} user={user} postId={1}/>
				</MemoryRouter>
			</Provider>
		);

        const replyButton = screen.getByTestId('ReplyButton');

        fireEvent.click(replyButton);

        const inputComment = screen.getByTestId('InputComment');

		fireEvent.change(inputComment, {
			target: {value: ""}
		});

		const commentButton = screen.getByTestId('CommentButton');

		fireEvent.click(commentButton);

		const errorInput = screen.getByTestId('ErrorInput');

	});
    
});