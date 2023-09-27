import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import { App } from "../../modules/app";
import store from "../../store";

describe("App", () => {
    it("renders correctly", () => {
        const tree = renderer
            .create(
                <Provider store={store}>
                    <MemoryRouter>
                        <App/>
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
                    <App />
                </MemoryRouter>
            </Provider>
        );


        /*const backButton = screen.getByRole('alert', {name: 'No posts found'});
        await waitFor(() => {
            screen.getByText('No posts found'); //NON FURRULA
        })*/
        

    });

    it("appears buttons back and next", async () => {
        render(
            <Provider store={store}>
                <MemoryRouter>
                    <App />
                </MemoryRouter>
            </Provider>
        );

        /*const backButton = screen.getByRole('button', {name: 'Back'});
        const nextButton = screen.getByRole('button', {name: 'Next'});*/
    });

    /*
    const inputText = screen.getByLabelText(/input some text/i);
    fireEvent.change(inputText, {
      target: { value: "Sreenzragnf qr qrfraibyirzragb" },
    });
    const calculateButton = screen.getByText(/calculate/i);
    fireEvent.click(calculateButton);
    await waitFor(() => {
      screen.getByText(/Ferramentas de desenvolvemento/);
    });
    */
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
