import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import Footer from "../src/components/general/Footer";

it("renders Footer correctly", () => {
  const tree = renderer
    .create(
      <Footer />
    )
    .toJSON();
  expect(tree).toMatchSnapshot();
});

