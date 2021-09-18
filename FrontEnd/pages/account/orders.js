import React from "react";
import AccountLayout from "../../src/components/layouts/AccountLayout";
import withSession from "../../src/lib/session";
import LeftMenuBar from "../../src/components/users/LeftMenuBar";
import useAxios from "axios-hooks";
import { isArray, isMap } from "lodash";
import { makeStyles } from "@material-ui/core/styles";
import EditAccountInformation from "../../src/components/users/EditAccountInformation";
import jwt_decode from "jwt-decode";
import TransactionsTable from "../../src/components/transactions/TransactionsTable";
import { Grid, Typography } from "@material-ui/core";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";

const useStyles = makeStyles((theme) => ({
  root: {
    marginLeft: theme.spacing(2),
    marginRight: theme.spacing(2),
  },
}));

export default function Orders({ token, user }) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_TRANSACTION_URL + "buyer/" + user.id
  );
  if (loading || error) {
    return <SimpleLoadingPlaceholder />;
  }
  return (
    <LeftMenuBar selectedTitle="Order History">
      <Grid container spacing={2} className={classes.root}>
        {data && isArray(data) ? (
          <Grid item xs={12}>
            <TransactionsTable token={token} transactions={data} refetch={refetch} />
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
    const user = jwt_decode(token);
    if (user.role == "ADMIN") {
      return { redirect: { destination: "/admin/books" } };
    }
    return {
      props: { token, user },
    };
  }

  return {
    redirect: {
      destination: "/login",
    },
  };
});
