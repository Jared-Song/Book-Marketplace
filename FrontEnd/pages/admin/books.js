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
import jwt_decode from "jwt-decode";

const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}));

export default function Books({ token }) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_BOOK_URL + "all"
  );

  const renderTable = () => {
    if (loading || error) {
      return <SimpleLoadingPlaceholder />;
    } else if (data) {
      return <BooksTable token={token} books={data} refetch={refetch} />;
    } else {
      <Typography variant="h5">No books in database.</Typography>;
    }
  };

  return (
    <LeftMenuBar selectedTitle="Books">
      <Grid container spacing={2} className={classes.root}>
        <Grid item xs={12}>
          <Grid container direction="row" justifyContent="flex-end">
            <Grid item>
              <CreateBook token={token} refetch={refetch} />
            </Grid>
          </Grid>
        </Grid>
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
