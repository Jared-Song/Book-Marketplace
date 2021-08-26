import React from "react";
import AccountLayout from "../src/components/layouts/AccountLayout";
import BookCard from "../src/components/normalUser/BookCard";
import Grid from "@material-ui/core/Grid";
import BookInfo from "../src/components/normalUser/BookInfo";

const menuItems = [
  {
    title: "My account",
    selected: false,
    onClick: () => {
      alert("123123");
    },
  },
  {
    title: "My account",
    selected: false,
    onClick: () => {
      alert("123123");
    },
  },
  {
    title: "My account",
    selected: true,
    onClick: () => {
      alert("123123");
    },
  },
  {
    title: "My account",
    selected: false,
    onClick: () => {
      alert("123123");
    },
  },
];

export default function test() {
  return (
    <AccountLayout menuItems={menuItems}>
      <Grid container style={{ paddingLeft: 50 }}>
        <Grid item xs={4} />
        <Grid item xs={8} style={{paddingBottom: 200}}>
          <BookInfo book={
            {
              title: "The Boy, The Mole, The Fox and The Horse",
              author: "Jimmy Qiu",
              ISBN: "000000000",
              score: 4.5,
              voteCount: 1000,
              price: 21.99,
              description: "A reminder of what truly matters, as told through the adventures of four beloved friends. Based on Charlie's daily Instagram. For fans of Winnie-the-pooh's Little Book of Wisdom."
            }
          } onPreview={() => {}} onAddToCart={() => {}} />

        </Grid>
      </Grid>
    </AccountLayout>
  );
}
