import { Grid } from "@material-ui/core";
import React from "react";
import Divider from "@material-ui/core/Divider";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,

  },
}));

export default function HorizontalMenu({ menuItems }) {
  const classes = useStyles();

  return (
    <Grid
      container
      direction="column"
      justifyContent="flex-start"
      alignItems="flex-start"
    >
      <div className={classes.root}>
        <List component="nav">
          {menuItems.map((menuItem) => (
            <ListItem
              button
              selected={menuItem.selected}
              onClick={menuItem.onClick}
              key={menuItem.title}
              disabled={menuItem.selected}
            >
              <ListItemText primary={menuItem.title} />
            </ListItem>
          ))}
        </List>
        <Divider />
      </div>
    </Grid>
  );
}
