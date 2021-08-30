import React from "react";
import BookInfo from "../src/components/general/BookInfo";
import Grid from "@material-ui/core/Grid";
import BigMenu from "../src/components/general/BigMenu";

export default function Book() {
  return (
    <div>
      <Grid container >
        <Grid item xs={2}>
          <BigMenu />
        </Grid>
        <Grid item xs={10}>
          <Grid container>
            <Grid item xs={4}>
              <img src="https://images-na.ssl-images-amazon.com/images/I/4169oZWBNZL.jpg"></img>
            </Grid>
            <Grid item xs={8} style={{ paddingBottom: 200 }}>
              <BookInfo
                book={{
                  title: "The Boy, The Mole, The Fox and The Horse",
                  author: "Aili Gong",
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
