import { Button, Dialog, DialogTitle, Grid, List, ListItem, Typography } from "@material-ui/core";
import React from "react";
import Rating from "@material-ui/lab/Rating";

export default function ReviewDialog() {
  const [open, setOpen] = React.useState(false)

  const handleClose = () => {
    setOpen(false)
  }

  return (
    <>
      <Button
        variant="outlined"
        onClick={() => {
          setOpen(true);
        }}
      >
        Review
      </Button>

      <Dialog onClose={handleClose} open={open}>
        <DialogTitle>Please review your purchase</DialogTitle>
        <List>
          <ListItem>
            <Typography>Rate your seller</Typography>
          </ListItem>
          <ListItem>
            <Rating />
          </ListItem>

          <ListItem>
            <Typography>Rate your book</Typography>
          </ListItem>
          <ListItem>
            <Rating />
          </ListItem>
        </List>
      </Dialog>
    </>
  );
}