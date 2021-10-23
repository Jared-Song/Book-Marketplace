import React from "react";
import useAxios from "axios-hooks";

//Components
import BigMenu from "../../src/components/general/BigMenu";
import BookListCard from "../../src/components/general/BookListCard";

//MUI
import Grid from "@material-ui/core/Grid";

const menuItems = [
  {
    title: "Home",
    selected: true,
  },
  {
    title: "New Releases",
    selected: false,
  },
  {
    title: "Coming Soon",
    selected: false,
  },
  {
    title: "Best Sellers",
    selected: false,
  },
  {
    title: "View All Books",
    selected: false,
  },
];
const categories = [
  {
    title: "Romance",
    selected: false,
  },
  {
    title: "Literature & Fiction",
    selected: false,
  },
  {
    title: "Textbooks & Study Guides",
    selected: false,
  },
  {
    title: "Mystery, Thriller & Suspense",
    selected: false,
  },
  {
    title: "Science Fiction & Fantasy",
    selected: false,
  },
  {
    title: "Children’s Books",
    selected: false,
  },
  {
    title: "Family & Lifestyle",
    selected: false,
  },
  {
    title: "Teen & Young Adult",
    selected: false,
  },
  {
    title: "Humour & Entertainment",
    selected: false,
  },
  {
    title: "Health, Fitness & Nutrition",
    selected: false,
  },
  {
    title: "Religion, philosophy & Social Sciences",
    selected: false,
  },
  {
    title: "Biographies & Memois",
    selected: false,
  },
];



export default function HomePage() {
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_BOOK_URL + "all"
  );

  if (loading && error) {
    return <SimpleLoadingPlaceholder />;
  }

  return (
    <Grid container>
      <Grid item xs={2}>
        <BigMenu />
      </Grid>
      {data && (
        <Grid item xs={10}>
          <BookListCard books={data} title="All Books" />
        </Grid>
      )}
    </Grid>
  );
}
