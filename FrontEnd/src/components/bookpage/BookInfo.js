import {
  Grid,
  Typography,
  makeStyles,
  Button,
  TextField,
} from "@material-ui/core";
import React from "react";
import Rating from "@material-ui/lab/Rating";
const useStyles = makeStyles((theme) => ({
  title: {
    fontSize: 24,
    fontWeight: 600,
  },
  raitingUserCount: {
    position: "relative",
    bottom: 7,
    left: theme.spacing(1),
  },
  priceAndQuantityInfo: {
    fontWeight: 500,
    fontSize: 18,
  },
  infoContainer: {
    paddingTop: theme.spacing(3),
  },
  cartButton: {
    width: 200,
    borderRadius: 14,
  },
  description:{
    margin: theme.spacing(1)
  }
}));
export default function BookInfo({ book, onPreview, onAddToCart }) {
  const classes = useStyles();

  return (
    <Grid container spacing={1}>
      <Grid item xs={12}>
        <Typography className={classes.title}>{book.title}</Typography>
      </Grid>
      <Grid item xs={12}>
        <Grid container>
          <Grid item xs={6}>
            <Typography>By {book.author}  (Author)</Typography>
          </Grid>
          <Grid item xs={6}>
            <Typography>ISBN: {book.ISBN}</Typography>
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={12} className={classes.description}>
        <Rating defaultValue={book.score} precision={0.5} readOnly />
        <Typography variant="caption" className={classes.raitingUserCount}>
          ({book.voteCount})
        </Typography>
      </Grid>
      <Grid item xs={12}>
        <Typography>
          {/* A reminder of what truly matters, as told through the adventures of
          four beloved friends. Based on Charlie's daily Instagram. For fans of
          Winnie-the-pooh's Little Book of Wisdom. */}
        </Typography>
      </Grid>
      <Grid item xs={12}>
        <Grid container justifyContent="center">
          <Button variant="contained" color="primary" onClick={onPreview}>
            Preview
          </Button>
        </Grid>
      </Grid>
      <Grid item xs={2} />
      <Grid item xs={4}>
        <Grid
          container
          justifyContent="space-between"
          className={classes.infoContainer}
        >
          <Grid item>
            <Typography className={classes.priceAndQuantityInfo}>
              Price:
            </Typography>
          </Grid>
          <Grid item>
            <Typography
              style={{ color: "red" }}
              className={classes.priceAndQuantityInfo}
            >
              ${book.price}
            </Typography>
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={6} />
      <Grid item xs={2} />
      <Grid item xs={4}>
        <Grid
          container
          justifyContent="space-between"
          className={classes.infoContainer}
        >
          <Grid item>
            <Typography className={classes.priceAndQuantityInfo}>
              Quantity:
            </Typography>
          </Grid>
          <Grid item>
            <TextField
              varaint="outlined"
              type="number"
              style={{ width: 55 }}
              defaultValue={1}
              InputLabelProps={{
                shrink: true,
              }}
            />
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={12}>
        <Grid
          container
          justifyContent="center"
          className={classes.infoContainer}
        >
          <Button
            variant="contained"
            color="secondary"
            className={classes.cartButton}
            onClick={onAddToCart}
          >
            Add to cart
          </Button>
        </Grid>
      </Grid>
    </Grid>
  );
}
