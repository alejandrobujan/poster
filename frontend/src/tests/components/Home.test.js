import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import Home from "../../modules/app/components/Home";
import store from "../../store";

describe("Home", () => {
    it("renders correctly", () => {
        const tree = renderer
            .create(
                <Provider store={store}>
                    <MemoryRouter>
                        <Home/>
                    </MemoryRouter>
                </Provider>
            )
            .toJSON();
        expect(tree).toMatchSnapshot();
    });


    it("no posts", async () => {
        render(
            <Provider store={store}>
                <MemoryRouter>
                    <Home />
                </MemoryRouter>
            </Provider>
        );

        screen.queryByText(/No posts found/i);

    });

    it("appears buttons back and next", async () => {
        render(
            <Provider store={store}>
                <MemoryRouter>
                    <Home />
                </MemoryRouter>
            </Provider>
        );

        const backButton = screen.findByRole('button', {name: 'Back'});
        const nextButton = screen.findByRole('button', {name: 'Next'});
    });
});

/*
fireEvent.change(inputText, {
            target: { value: "Sreenzragnf qr qrfraibyirzragb" },
        });
        const calculateButton = screen.getByText(/calculate/i);
        fireEvent.click(calculateButton);
        await waitFor(() => {
            screen.getByText(/Ferramentas de desenvolvemento/);
        });
*/
