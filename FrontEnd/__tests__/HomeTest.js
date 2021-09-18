import "@testing-library/jest-dom/extend-expect";
import Home from "../pages/index";
import { render, screen } from '@testing-library/react';

//dummy test
test("Check for Getting Started Text", () => {
  const { getByText } = render(<Home />);
  expect(getByText("New Releases")).toBeInTheDocument();
  expect(getByText("Best Sellers")).toBeInTheDocument();
  expect(getByText("Maybe You Like")).toBeInTheDocument();
});

