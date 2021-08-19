import { Button, Grid } from "@material-ui/core";
import React from "react";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
  selectedItem: {
    width: "100%",
    backgroundColor: "#EBF0FF",
    borderRadius: 15,
    cursor: 'pointer'
  },
  item: {
    width: "100%",
    cursor: 'pointer'
  },
}));

// menuItem shoud looks like: [{title: String, selected: Boolean!, action: () => void}]
export default function HorizontalMenu({ menuItems }) {
  const classes = useStyles();

  return (
    <Grid
      container
      direction="column"
      justifyContent="flex-start"
      alignItems="flex-start"
      spacing={2}
    >
      {menuItems.map((menuItem) => (
        <Grid
          item
          xs={12}
          key={menuItem.title}
          className={menuItem.selected ? classes.selectedItem : classes.item}
        >
          <Button disabled={menuItem.selected} onClick={menuItem.onClick}>{menuItem.title}</Button>
        </Grid>
      ))}
    </Grid>
  );
}
