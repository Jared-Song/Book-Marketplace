import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import HomePage from "../src/components/homepage/HomePage";

it("renders correctly", () => {
  const tree = renderer.create(<HomePage />).toJSON();
  expect(tree).toMatchSnapshot();
});

