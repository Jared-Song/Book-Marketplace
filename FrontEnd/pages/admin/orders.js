import { isArray } from "lodash";
import jwt_decode from "jwt-decode";
import React from "react";
import useAxios from "axios-hooks";

//Components
import LeftMenuBar from "../../src/components/admin/LeftMenuBar";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
import TransactionsTable from "../../src/components/transactions/TransactionsTable";
import withSession from "../../src/lib/session";

//MUI
import Grid from "@material-ui/core/Grid";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Typography from "@material-ui/core/Typography";

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

  const renderTable = () => {
    if (loading || error) {
      return <SimpleLoadingPlaceholder />;
    } else if (data && isArray(data) && data.length > 0) {
      return (
        <TransactionsTable
        type="orders"
          token={token}
          transactions={data}
          refetch={refetch}
          isAdmin={true}
        />
      );
    } else {
      return <Typography variant="h5">No order records!</Typography>;
    }
  };

  return (
    <LeftMenuBar selectedTitle="Order History">
      <Grid container spacing={2} className={classes.root}>
        <Grid item xs={12}>
          {renderTable()}
        </Grid>
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
