import React from "react";
import AccountLayout from "../layouts/AccountLayout";
import { useRouter } from "next/router";

export default function LeftMenuBar({ children, selectedTitle }) {
  const router = useRouter();
  const initialMenuItems = [
    {
      title: "My Account",
      selected: true,
      onClick: () => {
        router.push("/account")
      },
    },
    {
      title: "Personal Info",
      selected: false,
      onClick: () => {
        alert("123123");
      },
    },
    {
      title: "Order History",
      selected: false,
      onClick: () => {
        alert("123123");
      },
    },
    {
      title: "My Sale",
      selected: false,
      onClick: () => {
        alert("123123");
      },
    },
  ];

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

  return <AccountLayout menuItems={menuItems}>{children}</AccountLayout>;
}
