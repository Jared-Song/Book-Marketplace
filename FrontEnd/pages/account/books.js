import React from "react";
import LeftMenuBar from "../../src/components/users/LeftMenuBar";
import withSession from "../../src/lib/session";
import useAxios from "axios-hooks";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
import Grid from "@material-ui/core/Grid";
import CreateBook from "../../src/components/admin/books/CreateBook";
import BooksTable from "../../src/components/admin/books/BooksTable";
import makeStyles from "@material-ui/core/styles/makeStyles";
import jwt_decode from "jwt-decode";

const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}));

export default function Books({ token, userId }) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_BROWSE_URL + "sellerId/" + userId
  );

  if (loading && error) {
    return <SimpleLoadingPlaceholder />;
  }

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
        {data && (
          <Grid item xs={12}>
            <BooksTable token={token} books={data} refetch={refetch} />
          </Grid>
        )}
        {!data && (
          <Grid item xs={12}>
            No books on sale, please click the add button to sell your first book!
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
    const userId = user.id;
    return { props: { token, userId } };
  }

  return {
    redirect: {
      destination: "/login",
    },
  };
});
