import { isEmpty } from "lodash";
import jwt_decode from "jwt-decode";
import React from "react";
import useAxios from "axios-hooks";

//Components
import AccountLayout from "../../src/components/layouts/AccountLayout";
import ChangeAccountType from "../../src/components/users/ChangeAccountType";
import CloseAccount from "../../src/components/users/CloseAccount";
import EditAccountInformation from "../../src/components/users/EditAccountInformation";
import EditPassword from "../../src/components/users/EditPassword";
import LeftMenuBar from "../../src/components/users/LeftMenuBar";
import withSession from "../../src/lib/session";

//MUI
import makeStyles from "@material-ui/core/styles/makeStyles";

const useStyles = makeStyles((theme) => ({
  root: {
    marginLeft: theme.spacing(2),
    marginRight: theme.spacing(2)
  },
}));

export default function index({token, user}) {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_USERS_URL + user.id
  );
  return (
    <LeftMenuBar selectedTitle="My Account">
      <div className={classes.root}>
        {loading || isEmpty(data) ? <></> : 
        <>
        <EditAccountInformation user={data} refetch={refetch} token={token} />
        <EditPassword user={data} token={token} />
        <ChangeAccountType user={data} token={token}/>
        <CloseAccount user={data} token={token} />
        </>}
      </div>
    </LeftMenuBar>
  );
}

export const getServerSideProps = withSession(async function ({ req, res }) {
  const token = req.session.get("token");
  if (token) {
    const user = jwt_decode(token);
    if (user.role == "ADMIN") {
      return { redirect: { destination: "/admin/requests" } };
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

