import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import styles from "../../../styles/Home.module.css";

const useStyles = makeStyles((theme) => ({
  root: {
    minWidth: 500,
    margin: theme.spacing(1),
  },
  palceholder: {
    margin: 40,
  }
}));

export default function MyCard({ children }) {
  const classes = useStyles();

  return (
      <Card className={classes.root}>
        <div className={classes.palceholder}>

        {children}
        </div>
        </Card>
  );
}
