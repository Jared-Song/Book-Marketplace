import React from "react";
import AccountLayout from "../../src/components/layouts/AccountLayout";
import { Grid, Typography } from "@material-ui/core";
import withSession from "../../src/lib/session";
import LeftMenuBar from "../../src/components/users/LeftMenuBar";
import useAxios from "axios-hooks";
import { isEmpty } from "lodash";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
import { makeStyles } from "@material-ui/core/styles";
import EditAccountInformation from "../../src/components/users/EditAccountInformation";

const useStyles = makeStyles((theme) => ({
  root: {
    marginLeft: theme.spacing(2),
    marginRight: theme.spacing(2)
  },
}));

export default function index({token}) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_USERS_URL + "1"
  );
  return (
    <LeftMenuBar selectedTitle="My Account">
      <div className={classes.root}>
        {loading || isEmpty(data) ? <></> : <EditAccountInformation user={data} refetch={refetch} token={token} />}
      </div>
    </LeftMenuBar>
  );
}

export const getServerSideProps = withSession(async function ({ req, res }) {
  const token = req.session.get("token");
  if (token) {
    return {
      props: { token },
    };
  }

  return {
    redirect: {
      destination: "/login",
    },
  };
});
