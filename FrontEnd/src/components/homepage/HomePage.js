import React from "react";
import Grid from "@material-ui/core/Grid";
import BigMenu from "../general/BigMenu";
import BookListCard from "../general/BookListCard";

import useAxios from "axios-hooks";
import router from "next/router";

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
  const [{ data: book1, loading: loading1, error: error1 }, refetch1] = useAxios(
    process.env.NEXT_PUBLIC_BROWSE_URL + "newReleases/5"
  );

  const [{ data: book2, loading: loading2, error: error2 }, refetch2] = useAxios(
    process.env.NEXT_PUBLIC_BROWSE_URL + "bestSellers/5"
  );

  const [{ data: book3, loading: loading3, error: error3 }, refetch3] = useAxios(
    process.env.NEXT_PUBLIC_BROWSE_URL + "random/5"
  );

  if (loading1 && error1 ) {
    return <SimpleLoadingPlaceholder />;
  }
  if (loading2 && error2 ) {
    return <SimpleLoadingPlaceholder />;
  }

  if (loading3 && error3 ) {
    return <SimpleLoadingPlaceholder />;
  }
  return (
    <Grid container>
      <Grid item xs={2}>
        <BigMenu />
      </Grid>
      <Grid item xs={10}>
        {book1 && <BookListCard books={book1} title="New Release" />}
        {book2 && <BookListCard books={book2} title="Best Sellers" />}
        {book3 && (
          <BookListCard
            books={book3}
            title="Maybe You Like"
            handleClick={() => {
              router.push("/book/all");
            }}
          />
        )}
      </Grid>
    </Grid>
  );
}
