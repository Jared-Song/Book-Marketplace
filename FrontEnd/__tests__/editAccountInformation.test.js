import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import { AuthProvider } from "../src/context/AuthContext";
import { useRouter } from "next/router";
import { SnackbarProvider } from "notistack";
import EditAccountInformation from "../src/components/users/EditAccountInformation";

it("renders Edit Account Information correctly", () => {
  const tree = renderer
    .create(
      <SnackbarProvider maxSnack={3}>
        <AuthProvider token="testToken">
          <EditAccountInformation
            token="testToken"
            refetch={() => {}}
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
