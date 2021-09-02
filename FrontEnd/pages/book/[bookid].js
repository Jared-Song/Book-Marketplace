import React from "react";
import BookInfo from "../../src/components/bookpage/BookInfo";
import Grid from "@material-ui/core/Grid";
import BigMenu from "../../src/components/general/BigMenu";
import { useRouter } from "next/router";
import axios from "axios";
import { useCurrentUser } from "../../src/context/AuthContext";

export default function Book({ book }) {
  const router = useRouter();
  const { bookid } = router.query;

  return (
    <div>
      <Grid container>
        <Grid item xs={2}>
          <BigMenu />
        </Grid>
        <Grid item xs={10}>
          <Grid container>
            <Grid item xs={4}>
              <img src={book.imageURL}></img>
            </Grid>
            <Grid item xs={8} style={{ paddingBottom: 200 }}>
              <BookInfo
                book={{
                  title: book.title,
                  author: book.authorFirstName + " " + book.authorLastName,
                  ISBN: "000000000",
                  score: 4.5,
                  voteCount: 1000,
                  price: 21.99,
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
  const url = "http://localhost:8080/api/books/" + context.query.bookid;
  const token = "";
  const yourConfig = {
    headers: {
      Authorization: token,
    },
  };
  const book = await (await axios.get(url, yourConfig)).data;
  console.log(book);

  if (!book) {
    return {
      notFound: true,
    };
  }

  return {
    props: { book }, // will be passed to the page component as props
  };
}
