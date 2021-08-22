import React from "react";
import AccountLayout from "../../src/components/layouts/AccountLayout";
import BookCard from "../../src/components/normalUser/BookCard";
import Grid from "@material-ui/core/Grid";
import { Typography } from "@material-ui/core";
import withSession from "../../src/lib/session";

const menuItems = [
  {
    title: "My Account",
    selected: true,
    onClick: () => {
      alert("123123");
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

export default function index() {
  return (
    <AccountLayout menuItems={menuItems}>
      <Typography variant="title">User Page</Typography>
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