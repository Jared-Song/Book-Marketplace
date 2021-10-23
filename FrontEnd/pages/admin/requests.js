import React from "react";
import useAxios from "axios-hooks";
import { isArray, isEmpty } from "lodash";
import jwt_decode from "jwt-decode";

//Components
import CreateBook from "../../src/components/books/CreateBook";
import BooksTable from "../../src/components/books/BooksTable";
import LeftMenuBar from "../../src/components/admin/LeftMenuBar";
import RequestsTable from "../../src/components/admin/requests/RequestsTable";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
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

export default function Requests({ token }) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_REQUEST_URL + "all"
  );

  const renderTable = () => {
    if (loading || error) {
      return <SimpleLoadingPlaceholder />;
    } else if (data && isArray(data) && data.length > 0) {
      return <RequestsTable token={token} requests={data} refetch={refetch} />;
    } else {
      return <Typography variant="h5">Everything has been approved!</Typography>;
    }
  };

  return (
    <LeftMenuBar selectedTitle="Requests">
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
