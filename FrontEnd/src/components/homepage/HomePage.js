import React from "react";
import Grid from "@material-ui/core/Grid";
import BigMenu from "../general/BigMenu";
import BookListCard from "../general/BookListCard";

import useAxios from "axios-hooks";
import router from "next/router";



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
