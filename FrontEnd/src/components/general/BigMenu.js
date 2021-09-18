import HorizontalMenu from "./HorizontalMenu";
import React from "react";
import { useRouter } from "next/router";
import Router from "next/router";

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

export default function BigMenu({ selectedMenu, setSelectedMenu }) {
  const { pathname } = useRouter();
  const initialMenuItems = [
    {
      title: "Home",
      selected: false,
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
      title: "Maybe You Like",
      selected: false,
    },
  ];
  const menuItems = React.useMemo(() => {
    return initialMenuItems.map((menuItem) => {
      if (menuItem.title === selectedMenu) {
        return {
          ...menuItem,
          selected: true,
          onClick: () => {
            if(pathname === "/"){
              setSelectedMenu(menuItem.title);
            } else {
              Router.push("/?selectedMenu=" + menuItem.title)
            }
          },
        };
      }
      return {
        ...menuItem,
        onClick: () => {
          if (pathname === "/") {
            setSelectedMenu(menuItem.title);
          } else {
            Router.push("/?selectedMenu=" + menuItem.title);
          }
        },
      };
    });
  }, [selectedMenu, pathname]);
  return (
    <>
      <HorizontalMenu menuItems={menuItems} />
    </>
  );
}
