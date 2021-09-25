import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import HorizontalMenu from "../src/components/general/HorizontalMenu";

it("renders HorizontalMenu correctly", () => {
  const tree = renderer
    .create(
      <HorizontalMenu
        menuItems={[
          {
            title: "Requests",
            selected: false,
            onClick: () => {
              router.push("/admin/requests");
            },
          },
          {
            title: "Books",
            selected: false,
            onClick: () => {
              router.push("/admin/books");
            },
          },
          {
            title: "User Management",
            selected: false,
            onClick: () => {
              router.push("/admin/users");
            },
          },
          {
            title: "Order History",
            selected: false,
            onClick: () => {
              router.push("/admin/orders");
            },
          },
        ]}
      />
    )
    .toJSON();
  expect(tree).toMatchSnapshot();
});
