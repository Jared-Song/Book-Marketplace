import "@testing-library/jest-dom/extend-expect";
import renderer from "react-test-renderer";
import BookCard from "../src/components/general/BookCard";
import BookListCard from "../src/components/general/BookListCard";

it("renders bookCard correctly", () => {
  const tree = renderer
    .create(
      <BookCard
        book={{
          id: "1",
          title: "title",
          imageURL: "http://testImage.com",
          price: 1,
          rating: 1,
          ratingNo: 1,
        }}
      />
    )
    .toJSON();
  expect(tree).toMatchSnapshot();
});


it("renders Book list card correctly", () => {
  const tree = renderer
    .create(
      <BookListCard
        books={[
          {
            id: "1",
            title: "title",
            imageURL: "http://testImage.com",
            price: 1,
            rating: 1,
            ratingNo: 1,
          },
          {
            id: "2",
            title: "2title",
            imageURL: "http://2testImage.com",
            price: 2,
            rating: 2,
            ratingNo: 2,
          },
        ]}
        title="test title"
      />
    )
    .toJSON();
  expect(tree).toMatchSnapshot();
});

