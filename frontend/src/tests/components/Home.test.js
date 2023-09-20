import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import renderer from "react-test-renderer";
import Home from "../../modules/app/components/Home";

describe("Home", () => {
  it("renders correctly", () => {
    const tree = renderer
      .create(
        <MemoryRouter>
          <Home />
        </MemoryRouter>
      )
      .toJSON();
    expect(tree).toMatchSnapshot();
  });

  it("calculates the value as expected", async () => {
    render(
      <MemoryRouter>
        <Home />
      </MemoryRouter>
    );

    const inputText = screen.getByLabelText(/input some text/i);
    fireEvent.change(inputText, {
      target: { value: "Sreenzragnf qr qrfraibyirzragb" },
    });
    const calculateButton = screen.getByText(/calculate/i);
    fireEvent.click(calculateButton);
    await waitFor(() => {
      screen.getByText(/Ferramentas de desenvolvemento/);
    });
  });
});
