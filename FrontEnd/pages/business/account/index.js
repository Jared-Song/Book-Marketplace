import React from "react";
import AccountLayout from "../../../src/components/layouts/AccountLayout";
import { Typography } from "@material-ui/core";
import withSession from "../../../src/lib/session";

const menuItems = [
  {
    title: "My Account",
    selected: true,
    onClick: () => {
      alert("123123");
    },
  },
  {
    title: "Business Management",
    selected: false,
    onClick: () => {
      alert("123123");
    },
  },
  {
    title: "Order Management",
    selected: false,
    onClick: () => {
      alert("123123");
    },
  },
  {
    title: "Book Management",
    selected: false,
    onClick: () => {
      alert("123123");
    },
  },
];

export default function Account() {
  return (
    <AccountLayout menuItems={menuItems}>
      <Typography variant="title">Business User Account Page</Typography>
    </AccountLayout>
  );
}

export const getServerSideProps = withSession(async function ({ req, res }) {
  const user = req.session.get("user");

  if (user === undefined) {
    res.setHeader("location", "/login");
    res.statusCode = 302;
    res.end();
    return { props: {} };
  }

  return {
    props: { user: req.session.get("user") },
  };
});