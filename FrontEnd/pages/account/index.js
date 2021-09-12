import React from "react";
import AccountLayout from "../../src/components/layouts/AccountLayout";
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
      <Typography variant="h5">User Page</Typography>
    </AccountLayout>
  );
}

export const getServerSideProps = withSession(async function ({ req, res }) {
  const token = req.session.get("token");
  if (token) {
    return {
      props: {}
    }
  }

  return {
    redirect: {
      destination: "/login",
    },
  };
});