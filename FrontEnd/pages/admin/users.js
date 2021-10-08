import React from "react";
import LeftMenuBar from "../../src/components/admin/LeftMenuBar";
import withSession from "../../src/lib/session";
import useAxios from "axios-hooks";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
import { isEmpty } from "lodash";
import Grid from "@material-ui/core/Grid";
import CreateBook from "../../src/components/admin/books/CreateBook";
import BooksTable from "../../src/components/admin/books/BooksTable";
import { makeStyles } from "@material-ui/core/styles";
import UsersTable from "../../src/components/admin/users/UsersTable";
import jwt_decode from "jwt-decode";

const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(2)
  }
}));

export default function Users({ token }) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_USERS_URL + "all"
  );

  const renderTable = () => {
    if (loading || error) {
      return <SimpleLoadingPlaceholder />;
    } else if (data) {
      return <UsersTable token={token} users={data} refetch={refetch} />;
    } else {
      <Typography variant="h5">No user record!</Typography>;
    }
  };

  return (
    <LeftMenuBar selectedTitle="User Management">
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
