import axios from "axios";
import React from "react";

//Components
import BookInfo from "../../src/components/bookpage/BookInfo";

//MUI
import BigMenu from "../../src/components/general/BigMenu";
import Grid from "@material-ui/core/Grid";
import makeStyles from "@material-ui/core/styles/makeStyles";

const useStyles = makeStyles((theme) => ({
  main: {
    padding: 10, 
  },
  bookinfo: {
    padding: theme.spacing(2),
    paddingLeft: theme.spacing(4),
  },
}));

export default function Book({ book }) {
  const classes = useStyles();

  return (
    <div>
      <Grid container>
        <Grid item xs={2}>
          <BigMenu />
        </Grid>
        <Grid item xs={10}>
          <Grid container className={classes.main}>
            <Grid item m={4}>
              <img src={book.imageURL[0].url} width={400}></img>
            </Grid>
            <Grid item xs={7} className={classes.bookinfo}>
              <BookInfo
                book={{
                  title: book.title,
                  author: book.authorName,
                  ISBN: book.isbn,
                  id: book.id,
                  score: book.ratingTotal/book.ratingNo,
                  voteCount: book.ratingNo,
                  price: book.price,
                }}
              />
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </div>
  );
}

export async function getServerSideProps(context) {
  const url = process.env.NEXT_PUBLIC_BOOK_URL + context.query.bookid;
  const { data } = await axios.get(url);

  if (!data) {
    return {
      notFound: true,
    };
  }

  return {
    props: { book: data }, // will be passed to the page component as props
  };
}
