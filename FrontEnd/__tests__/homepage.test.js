import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import HomePage from "../src/components/homepage/HomePage";


jest.mock("next/router", () => ({
  useRouter() {
    return {
      route: "/",
      pathname: "",
      query: "",
      asPath: "",
    };
  },
}));


it("renders correctly", () => {
  const tree = renderer.create(<HomePage />).toJSON();
  expect(tree).toMatchSnapshot();
});

