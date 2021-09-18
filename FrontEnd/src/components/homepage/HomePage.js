import React from "react";
import Grid from "@material-ui/core/Grid";
import BigMenu from "../general/BigMenu";
import BookListCard from "../general/BookListCard";

import useAxios from "axios-hooks";
import router from "next/router";

export default function HomePage() {
  const [selectedMenu, setSelectedMenu] = React.useState("Home");
  const [newReleaseNumber, setNewReleaseNumber] = React.useState(4);
  const [bestSellersNumber, setBestSellersNumber] = React.useState(4);
  const [randomNumber, setRandomNumber] = React.useState(4);
  React.useEffect(() => {
    if (selectedMenu === "New Releases") {
      setNewReleaseNumber(20);
    } else if (selectedMenu === "Best Sellers") {
      setBestSellersNumber(20);
    } else if (selectedMenu === "Maybe You Like") {
      setRandomNumber(20);
    } else {
      setNewReleaseNumber(4);
      setBestSellersNumber(4);
      setRandomNumber(4);
    }
  }, [selectedMenu]);

  const [{ data: book1, loading: loading1, error: error1 }, refetch1] =
    useAxios(
      process.env.NEXT_PUBLIC_BROWSE_URL + `newReleases/${newReleaseNumber}`
    );

  const [{ data: book2, loading: loading2, error: error2 }, refetch2] =
    useAxios(
      process.env.NEXT_PUBLIC_BROWSE_URL + `bestSellers/${bestSellersNumber}`
    );

  const [{ data: book3, loading: loading3, error: error3 }, refetch3] =
    useAxios(process.env.NEXT_PUBLIC_BROWSE_URL + `random/${randomNumber}`);

  if (loading1 && error1) {
    return <SimpleLoadingPlaceholder />;
  }
  if (loading2 && error2) {
    return <SimpleLoadingPlaceholder />;
  }

  if (loading3 && error3) {
    return <SimpleLoadingPlaceholder />;
  }
  return (
    <Grid container>
      <Grid item xs={2}>
        <BigMenu
          selectedMenu={selectedMenu}
          setSelectedMenu={setSelectedMenu}
        />
      </Grid>
      <Grid item xs={10}>
        {book1 && ["Home", "New Releases"].includes(selectedMenu) && (
          <BookListCard
            books={book1}
            title="New Releases"
            handleClick={
              selectedMenu !== "New Releases"
                ? () => {
                    setNewReleaseNumber((old) => {
                      return old + 4;
                    });
                  }
                : undefined
            }
          />
        )}
        {book2 && ["Home", "Best Sellers"].includes(selectedMenu) && (
          <BookListCard
            books={book2}
            title="Best Sellers"
            handleClick={
              selectedMenu !== "Best Sellers"
                ? () => {
                    setBestSellersNumber((old) => {
                      return old + 4;
                    });
                  }
                : undefined
            }
          />
        )}
        {book3 && ["Home", "Maybe You Like"].includes(selectedMenu) && (
          <BookListCard
            books={book3}
            title="Maybe You Like"
            handleClick={
              selectedMenu !== "Maybe You Like"
                ? () => {
                    setRandomNumber((old) => {
                      return old + 4;
                    });
                  }
                : undefined
            }
          />
        )}
      </Grid>
    </Grid>
  );
}
