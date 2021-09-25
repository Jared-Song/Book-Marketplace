import React from "react";
import AccountLayout from "../layouts/AccountLayout";
import { useRouter } from "next/router";

export default function LeftMenuBar({ children, selectedTitle }) {
  const router = useRouter();
  const initialMenuItems = [
    {
      title: "My Account",
      selected: false,
      onClick: () => {
        router.push("/account")
      },
    },
    {
      title: "Order History",
      selected: false,
      onClick: () => {
        router.push("/account/orders");
      },
    },
    {
      title: "Transactions",
      selected: false,
      onClick: () => {
        router.push("/account/transactions");
      },
    },
    {
      title: "Books",
      selected: false,
      onClick: () => {
        router.push("/account/books");
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
