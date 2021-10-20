import { Button, Dialog, DialogTitle, Grid, List, ListItem, Typography } from "@material-ui/core";
import React from "react";
import Rating from "@material-ui/lab/Rating";
import { useSnackbar } from "notistack";
import axios from "axios"
import { useCurrentUser } from "../../context/AuthContext";

export default function ReviewDialog({ order, refetch }) {
  const [open, setOpen] = React.useState(false);
  const { enqueueSnackbar } = useSnackbar();
  const [sellerRate, setSellerRate] = React.useState(0);
  const [bookRate, setBookRate] = React.useState(0);
  const handleClose = () => {
    setOpen(false);
  };
  const { currentUser } = useCurrentUser();
  const saveRate = async () => {
    try {
      const response = await axios.post(
        process.env.NEXT_PUBLIC_REVIEW_URL + "addReview",
        {
          reviewerId: currentUser.id,
          transactionId: order.id,
          bookRating: bookRate,
          userRating: sellerRate,
        }
      );
      handleClose();
      refetch()
    } catch (error) {
      console.log(error);
      enqueueSnackbar("Something is wrong!", {
        variant: "error",
      });
    }
  };

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
            <Rating
              onChange={(e) => {
                setSellerRate(e.target.value);
              }}
            />
          </ListItem>

          <ListItem>
            <Typography>Rate your book</Typography>
          </ListItem>
          <ListItem>
            <Rating
              onChange={(e) => {
                setBookRate(e.target.value);
              }}
            />
          </ListItem>
          <ListItem>
            <Button variant="outlined" onClick={saveRate}>
              Save
            </Button>
          </ListItem>
        </List>
      </Dialog>
    </>
  );
}