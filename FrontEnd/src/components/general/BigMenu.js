import HorizontalMenu from "./HorizontalMenu";
import React from "react";

const initialMenuItems = [
  {
    title: "Home",
    selected: true,
  },
  {
    title: "New Releases",
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
    title: "Biographies & Memoirs",
    selected: false,
  },
];

export default function BigMenu({selectedTitle}) {
  const menuItems = React.useMemo(() => {
    return initialMenuItems.map((menuItem) => {
      if (menuItem.title === selectedTitle) {
        return {
          ...menuItem,
          selected: true,
        };
      }
      return menuItem;
    });
  }, [selectedTitle]);
  return (
    <>
      <HorizontalMenu menuItems={menuItems} />
      {/* <HorizontalMenu menuItems={categories} /> */}
    </>
  );
}
