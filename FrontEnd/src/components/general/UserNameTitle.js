import Typography from "@material-ui/core/Typography";
import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import FaceIcon from '@material-ui/icons/Face';

const useStyles = makeStyles((theme) => ({
  title: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
  },
  
}));
export default function UserNameTitle() {
  const classes = useStyles();

    return (
        <div className={classes.title}>
            <FaceIcon style={{ fontSize: 50 }}/>
            <Typography variant="h4">Hello, User Name!</Typography>
        </div>
    )
}
