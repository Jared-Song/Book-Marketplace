import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import HorizontalMenu from "../src/components/general/HorizontalMenu";
import { AuthProvider } from "../src/context/AuthContext";
import { useRouter } from "next/router";
import EditPassword from "../src/components/users/EditPassword";
import { SnackbarProvider } from "notistack";

it("renders Edit Password correctly", () => {
  const tree = renderer
    .create(
      <SnackbarProvider maxSnack={3}>
        <AuthProvider token="testToken">
          <EditPassword
            token="testToken"
            user={{
              id: "1",
              address: "address",
              username: "username",
              fullName: "fullName",
              role: "ADMIN",
              rating: 0,
              ratingNo: 0,
              userStatus: "userStatus",
            }}
          />
        </AuthProvider>
      </SnackbarProvider>
    )
    .toJSON();
  expect(tree).toMatchSnapshot();
});
