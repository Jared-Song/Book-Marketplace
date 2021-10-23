import { isArray } from "lodash";
import React from "react";
import useAxios from "axios-hooks";

//Components
import LeftMenuBar from "../../src/components/users/LeftMenuBar";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
import withSession from "../../src/lib/session";

//MUI
import Grid from "@material-ui/core/Grid";
import jwt_decode from "jwt-decode";
import makeStyles from "@material-ui/core/styles/makeStyles";
import TransactionsTable from "../../src/components/transactions/TransactionsTable";
import Typography from "@material-ui/core/Typography";

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
      <Typography variant="h5">No order history found!</Typography>;
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
