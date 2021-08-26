import {
  Card,
  CardActionArea,
  CardMedia,
  Typography,
  makeStyles,
  Grid,
} from "@material-ui/core";
import Rating from "@material-ui/lab/Rating"
import React from "react";

const useStyles = makeStyles((theme) => ({
  bookImage: {
    width: 160,
    height: 200,
    marginLeft: 4,
  },
  root: {
    width: 170,
    overflow: "hidden",
    paddingTop: theme.spacing(1),
    paddingBottom: theme.spacing(1),
    display: "inline-block",
  },
  description: {
    fontSize: 12,
    overflow: "hidden",
  },
  contentContainer: {
    height: 80,
    overflow: "hidden",
    padding: 6,
  },
  raitingUserCount: {
    position: "relative",
    bottom: 7,
    left: theme.spacing(1)
  }
}));

export default function BookCard({ book }) {
  const { url, title, price, rating, raitingUserCount } = book;
  const classes = useStyles();
  return (
    <Card className={classes.root}>
      <CardActionArea>
        <CardMedia image={url} title={title} className={classes.bookImage} />
        <Grid container  className={classes.contentContainer}>
            <Grid item xs={12}>
                <Typography>
                    ${price}    
                </Typography>    
            </Grid>   
            <Grid item xs={12}>
                <Typography noWrap alt={title} className={classes.title}>
                {title}
                </Typography>
            </Grid>
            <Grid item xs={12}>
                <Rating
                  defaultValue={rating}
                  precision={0.5}
                  readOnly />
                  <Typography variant="caption" className={classes.raitingUserCount}>
                    ({raitingUserCount})
                  </Typography>
            </Grid>
        </Grid>
      </CardActionArea>
    </Card>
  );
}
