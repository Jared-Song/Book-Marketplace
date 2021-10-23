import React from "react";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
  root: {
    margin: 45,
  },
}));

export default function FourOFour() {
  const classes = useStyles();

  return (
    <Grid containter className={classes.root}>
      <img src="error.svg" />
      <Typography variant="h4">Whoops! We can't find that page.</Typography>
    </Grid>
  );
}
