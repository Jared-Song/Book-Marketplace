import React from "react";
import BookInfo from "../../src/components/bookpage/BookInfo";
import Grid from "@material-ui/core/Grid";
import BigMenu from "../../src/components/general/BigMenu";
import axios from "axios";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
  main: {
    // margin: theme.spacing(2)
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
              <img src={book.imageURL} width={400}></img>
            </Grid>
            <Grid item xs={8} className={classes.bookinfo}>
              <BookInfo
                book={{
                  title: book.title,
                  author: book.authorFirstName + " " + book.authorLastName,
                  ISBN: book.isbn,
                  // TODO - Missing score and votecount
                  score: 4.5,
                  voteCount: 1000,
                  price: book.price,
                  // TODO - Missing description
                  description:
                    "A reminder of what truly matters, as told through the adventures of four beloved friends. Based on Charlie's daily Instagram. For fans of Winnie-the-pooh's Little Book of Wisdom.",
                }}
                onPreview={() => {}}
                onAddToCart={() => {}}
              />
            </Grid>
          </Grid>
          <Grid container>Reviews List </Grid>
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
