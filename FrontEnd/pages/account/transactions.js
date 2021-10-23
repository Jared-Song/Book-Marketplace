import React from "react";
import useAxios from "axios-hooks";
import { isArray } from "lodash";
import jwt_decode from "jwt-decode";

//Components
import LeftMenuBar from "../../src/components/users/LeftMenuBar";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
import TransactionsTable from "../../src/components/transactions/TransactionsTable";
import withSession from "../../src/lib/session";

//MUI
import Grid from "@material-ui/core/Grid";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
  root: {
    marginLeft: theme.spacing(2),
    marginRight: theme.spacing(2),
  },
}));

export default function Transactions({ token, user }) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_TRANSACTION_URL + "seller/" + user.id
  );

  const renderTable = () => {
    if (loading || error) {
      return <SimpleLoadingPlaceholder />;
    } else if (data && isArray(data) && data.length > 0) {
      return (
        <TransactionsTable
          token={token}
          transactions={data}
          refetch={refetch}
          type="transactions"
        />
      );
    } else {
      <Typography variant="h5">No transation history found!</Typography>;
    }
  };

  return (
    <LeftMenuBar selectedTitle="Transactions">
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
