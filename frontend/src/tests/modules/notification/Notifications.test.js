import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import { Provider } from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'

import renderer from "react-test-renderer";
import { notificationMock } from "../../state/Notification.mock";

import { Notifications } from "../../../modules/notification";

describe("Notifications", () => {

	const middlewares = [thunk];
	const mockStore = configureMockStore(middlewares);
	let store;
	store = mockStore(notificationMock);

	const emptyNotifications =[];

	const notifications =
		[
			{
				notificationId: 1,
				notificationText: "Hola",
				notificationViewed: false,
				notificationCreationDate: null,
				postId: 1,
				notifierUserDto: {
					avatar: null
				}
			}
		];


	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<MemoryRouter>
						<Notifications notifications={notifications} />
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

	it("basic structure no notifications", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<Notifications notifications={emptyNotifications} />
				</MemoryRouter>
			</Provider>
		);

		const noNotifications = screen.getByTestId('NoNotifications');

	});

	it("basic structure notifications", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<Notifications notifications={notifications} />
				</MemoryRouter>
			</Provider>
		);

		const notificationsScreen = screen.getByTestId('Notifications');

	});

});