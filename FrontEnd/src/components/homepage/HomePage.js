import React from "react";
import Grid from "@material-ui/core/Grid";
import BigMenu from "../general/BigMenu";
import BookListCard from "../general/BookListCard";

import useAxios from "axios-hooks";
import router from "next/router";

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
const books = [
  book,
  { ...book, id: 2 },
  { ...book, id: 3 },
  { ...book, id: 4 },
  { ...book, id: 5 },
];

export default function HomePage() {
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_BOOK_URL + "all"
  );

  if (loading && error) {
    return <SimpleLoadingPlaceholder />;
  }

  return (
    <Grid container>
      <Grid item xs={2}>
        <BigMenu />
      </Grid>
      {data && (
        <Grid item xs={10}>
          <BookListCard books={data} title="New Release" 
          />
          <BookListCard books={data} title="Best Sellers" />
          <BookListCard books={data} title="Maybe You Like" handleClick ={()=>{
            router.push('/book/all')
          }}/>
        </Grid>
      )}
    </Grid>
  );
}
