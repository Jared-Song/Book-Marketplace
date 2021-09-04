import { Container, Grid, Typography } from "@material-ui/core";
import React from "react";
import HorizontalMenu from "../general/HorizontalMenu";
import { makeStyles } from "@material-ui/core/styles";
import UserNameTitle from "../general/UserNameTitle";

const useStyles = makeStyles((theme) => ({
  root:{
    margin: "65px 0px",
  },
  main: {
    // backgroundColor: "lightgrey",    
  },
  menu: {
    // backgroundColor: "pink",
  },
}));

export default function AccountLayout({ children, menuItems }) {
  const classes = useStyles();
  return (
    <Container maxWidth="lg" className={classes.root}>
      <Grid container>
        <Grid item xs={12}>
          <Grid container>
            <Grid item xs={3} />
            <Grid item xs={9}>
              <UserNameTitle />
            </Grid>
          </Grid>
        </Grid>

        <Grid item xs={12}>
          <Grid container>
            <Grid item xs={3} className={classes.menu}>
              <HorizontalMenu menuItems={menuItems} />
            </Grid>
            <Grid tem xs={9} className={classes.main}>
              {children}
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </Container>
  );
}
