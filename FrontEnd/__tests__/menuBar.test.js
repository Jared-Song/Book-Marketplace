import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import HorizontalMenu from "../src/components/general/HorizontalMenu";
import PrimarySearchAppBar from "../src/components/general/MenuBar";
import { AuthProvider } from "../src/context/AuthContext";
import { useRouter } from "next/router";

jest.mock("next/router", () => ({
  useRouter() {
    return {
      route: "/",
      pathname: "",
      query: "keyword=test&filter=test",
      asPath: "",
    };
  },
}));

it("renders Search Bar correctly", () => {
  const tree = renderer
    .create(
      <AuthProvider token="">
        <PrimarySearchAppBar />
      </AuthProvider>
    )
    .toJSON();
  expect(tree).toMatchSnapshot();
});
