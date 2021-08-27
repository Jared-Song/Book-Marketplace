import HorizontalMenu from "./HorizontalMenu";
import React from "react";
import Grid from "@material-ui/core/Grid";
import BookCard from "./BookCard"
const menuItems = [
  {
    title: "Home",
    selected: true,
  },
  {
    title: "New Releases",
    selected: false,
  },
  {
    title: "Comming Soon",
    selected: false,
  },
  {
    title: "Best Sellers",
    selected: false,
  },
  {
    title: "View All Books",
    selected: false,
  },
];
const categories = [
  {
    title: "Romance",
    selected: false,
  },
  {
    title: "Literature & Fiction",
    selected: false,
  },
  {
    title: "Textbooks & Study Guides",
    selected: false,
  },
  {
    title: "Mystery, Thriller & Suspense",
    selected: false,
  },
  {
    title: "Science Fiction & Fantasy",
    selected: false,
  },
  {
    title: "Children’s Books",
    selected: false,
  },
  {
    title: "Family & Lifestyle",
    selected: false,
  },
  {
    title: "Teen & Young Adult",
    selected: false,
  },
  {
    title: "Humour & Entertainment",
    selected: false,
  },
  {
    title: "Health, Fitness & Nutrition",
    selected: false,
  },
  {
    title: "Religion, philosophy & Social Sciences",
    selected: false,
  },
  {
    title: "Biographies & Memoies",
    selected: false,
  },
];

export default function HomePage() {
  return (
    <Grid container>
      <Grid item xs={2}>
        <HorizontalMenu menuItems={menuItems} />
        <HorizontalMenu menuItems={categories} />
      </Grid>
      <Grid item xs={10}>
        {/* <BookCard/> */}
      </Grid>

    </Grid>
  );
}
