import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../../store";

import { Comments } from "../../../modules/comment";

describe("Comments", () => {

    const user = {
        avatar: null,
        userName: "Pepe"
    }

    const emptyComments = [];
    const oneComment = [{id: 1, description: "Hola", date: 10382349823, answers: 2, parentId: null, level: 1, userSummaryDto: user}];

    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<Comments comments={emptyComments} postId={1}/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

    it("empty comments", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <Comments comments={emptyComments} postId={1}/>
				</MemoryRouter>
			</Provider>
		);

		const noComments = screen.getByTestId('NoComments');

	});

    it("not empty comments", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <Comments comments={oneComment} postId={1}/>
				</MemoryRouter>
			</Provider>
		);

		const comments = screen.getByTestId('ManyComments');

	});
    
});