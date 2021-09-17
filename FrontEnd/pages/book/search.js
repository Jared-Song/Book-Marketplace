import React from "react";
import withSession from "../../src/lib/session";
import { useRouter } from "next/router";
import useAxios from "axios-hooks";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import BigMenu from "../../src/components/general/BigMenu";
import { makeStyles } from "@material-ui/core/styles";
import BookCard from "../../src/components/general/BookCard";

const useStyles = makeStyles((theme) => ({
  list: {
    padding: theme.spacing(2),
    direction: "row",
    justifyContent: "space-around",
    alignItems: "left",
  },
}));

export default function Search() {
  const { query } = useRouter();
  const { keyword, filter } = query;
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_BROWSE_URL + filter + "/" + keyword
  );
  const classes = useStyles();
  const renderContent = () => {
    if (data && data.length > 0) {
      return (
        <>
          {data.map((book) => {
            return <BookCard key={book.id} book={book} />;
          })}
        </>
      );
    }
    return <Typography variant="h2">No result found!</Typography>;
  };

  return (
    <Grid container>
      <Grid item xs={2}>
        <BigMenu />
      </Grid>
      <Grid item xs={10}>
        <Grid container className={classes.list}>
          {renderContent()}
        </Grid>
      </Grid>
    </Grid>
  );
}
