import { Button, Grid, Typography } from "@material-ui/core";
import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import BookCard from "./BookCard";
import { lightBlue } from "@material-ui/core/colors";

const useStyles = makeStyles((theme) => ({
  root: {
    // width: "100%",
    backgroundColor: theme.palette.background.paper,
    marginBottom: theme.spacing(2),
  },
  head: {
    backgroundColor: theme.palette.primary.light,
    height: "50px",
    direction: "row",
    justifyContent: "flex-start",
    alignItems: "center",
  },
  title:{
    marginLeft: theme.spacing(1),

    marginRight: theme.spacing(3),
  },
  list: {
    padding: theme.spacing(2),
    direction: "row",
    justifyContent: "space-around",
    alignItems: "left",
  },
}));

export default function BookListCard({ books, title, handleClick }) {
  const classes = useStyles();

  return (
    <Grid container className={classes.root}>
      <Grid item xs={12}>
        <Grid container className={classes.head}>
          <Typography variant="h5" className={classes.title}>{title}</Typography>
          {
            handleClick &&
          <Button onClick={handleClick}>view more {">>"}</Button>

          }
        </Grid>
      </Grid>
      <Grid item xs={12}>
        <Grid container className={classes.list}>
          {books.map((book) => {
            return <BookCard key={book.id} book={book} />;
          })}
        </Grid>
      </Grid>
    </Grid>
  );
}
