import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import HorizontalMenu from "../src/components/general/HorizontalMenu";
import PrimarySearchAppBar from "../src/components/general/MenuBar";
import { AuthProvider } from "../src/context/AuthContext";
import { useRouter } from "next/router";
import UserNameTitle from "../src/components/general/UserNameTitle";

it("renders UserNameTitle when there is no user", () => {
  const tree = renderer
    .create(
      <AuthProvider token="">
        <UserNameTitle />
      </AuthProvider>
    )
    .toJSON();
  expect(tree).toMatchSnapshot();
});
