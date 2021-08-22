import Typography from "@material-ui/core/Typography";
import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import FaceIcon from "@material-ui/icons/Face";
import { useCurrentUser } from "../../context/AuthContext";
import { useRouter } from "next/router";

const useStyles = makeStyles((theme) => ({
  title: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
  },
}));
export default function UserNameTitle() {
  const router = useRouter();

  const classes = useStyles();
  const { currentUser } = useCurrentUser();

  if (currentUser) {
    return (
      <div className={classes.title}>
        <FaceIcon style={{ fontSize: 50 }} />
        <Typography variant="h4">Hello, {currentUser.username}!</Typography>
      </div>
    );
  }
  return null;
}
