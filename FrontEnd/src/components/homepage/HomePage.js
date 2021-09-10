import React from "react";
import Grid from "@material-ui/core/Grid";
import BigMenu from "../general/BigMenu";
import BookListCard from "../general/BookListCard";
import axios from "axios";
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
    title: "Coming Soon",
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
    title: "Biographies & Memois",
    selected: false,
  },
];

const book = {
  id: 1,
  url: "https://images-na.ssl-images-amazon.com/images/I/4169oZWBNZL.jpg",
  title: "The Boy, The Mole, The Fox and The Horse",
  price: 10.99,
  rating: 4,
  raitingUserCount: 100,
};
const books = await axios.get(process.env.NEXT_PUBLIC_ALL_BOOK_URL)

export default function HomePage() {
  return (
    <Grid container>
      <Grid item xs={2}>
        <BigMenu />
      </Grid>
      <Grid item xs={10}>
        <BookListCard books={books} title="New Release" />
        <BookListCard books={books} title="Best Sellers" />
        <BookListCard books={books} />
      </Grid>
    </Grid>
  );
}
