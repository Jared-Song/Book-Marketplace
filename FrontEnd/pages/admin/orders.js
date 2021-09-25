import React from "react";
import LeftMenuBar from "../../src/components/admin/LeftMenuBar";
import withSession from "../../src/lib/session";
import useAxios from "axios-hooks";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
import { isArray, isEmpty } from "lodash";
import Grid from "@material-ui/core/Grid";
import CreateBook from "../../src/components/admin/books/CreateBook";
import BooksTable from "../../src/components/admin/books/BooksTable";
import { makeStyles } from "@material-ui/core/styles";
import jwt_decode from "jwt-decode";
import { Typography } from "@material-ui/core";
import TransactionsTable from "../../src/components/transactions/TransactionsTable";

const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}));

export default function Orders({ token }) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_TRANSACTION_URL + "all"
  );

  if (loading && error) {
    return <SimpleLoadingPlaceholder />;
  }

  return (
    <LeftMenuBar selectedTitle="Order History">
    <Grid container spacing={2} className={classes.root}>
      {data && isArray(data) ? (
        <Grid item xs={12}>
          <TransactionsTable token={token} transactions={data} refetch={refetch}  isAdmin={true}/>
        </Grid>
      ) : (
        <Grid item xs={12}>
        <Typography variant="h5">No order history found!</Typography>
      </Grid>
      )}
    </Grid>
  </LeftMenuBar>
  );
}

export const getServerSideProps = withSession(async function ({ req, res }) {
  const token = req.session.get("token");
  if (token) {
    const { role } = jwt_decode(token);
    if (role !== "ADMIN") {
      return { redirect: { destination: "/account" } };
    }
    return { props: { token } };
  }

  return {
    redirect: {
      destination: "/login",
    },
  };
});
