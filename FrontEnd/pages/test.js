import React from "react";
import AccountLayout from "../src/components/layouts/AccountLayout";
import BookCard from "../src/components/normalUser/BookCard";
import Grid from "@material-ui/core/Grid";

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
        <Grid item xs={3}>
          <BookCard
            book={{
              title: "Clean Co12312312 sd ds de 2021",
              url: "https://via.placeholder.com/140x200.png",
              price: 12.1,
              rating: 3.5,
              raitingUserCount: 10,
            }}
          />
        </Grid>
        <Grid item xs={3}>
          <BookCard
            book={{
              title: "Clean Co12312312 sd ds de 2021",
              url: "https://via.placeholder.com/140x200.png",
              price: 12.1,
              rating: 3.5,
              raitingUserCount: 10,
            }}
          />
        </Grid>
        <Grid item xs={3}>
          <BookCard
            book={{
              title: "Clean Co12312312 sd ds de 2021",
              url: "https://via.placeholder.com/140x200.png",
              price: 12.1,
              rating: 3.5,
              raitingUserCount: 10,
            }}
          />
        </Grid>
        <Grid item xs={3}>
          <BookCard
            book={{
              title: "Clean Co12312312 sd ds de 2021",
              url: "https://via.placeholder.com/140x200.png",
              price: 12.1,
              rating: 3.5,
              raitingUserCount: 10,
            }}
          />
        </Grid>
      </Grid>
    </AccountLayout>
  );
}
