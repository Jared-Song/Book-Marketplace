import {
  Card,
  CardActionArea,
  CardMedia,
  Typography,
  makeStyles,
  Grid,
} from "@material-ui/core";
import Rating from "@material-ui/lab/Rating"
import Router from "next/router";
import React from "react";

const useStyles = makeStyles((theme) => ({
  bookImage: {
    width: 160,
    height: 200,
    marginLeft: 20,
  },
  root: {
    width: 200,
    overflow: "hidden",
    marginTop: theme.spacing(1),
    display: "inline-block",
    marginBottom: theme.spacing(1),
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
  const { imageURL, title, price, ratingTotal, ratingNo } = book;
  const classes = useStyles();
  return (
    <Card className={classes.root}>
      <CardActionArea onClick={()=>{
        Router.push("/book/"+book.id);
      }}>
        <CardMedia image={imageURL[0].url} title={title} className={classes.bookImage} />
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
                  defaultValue={ratingTotal/ratingNo}
                  precision={0.5}
                  readOnly />
                  <Typography variant="caption" className={classes.raitingUserCount}>
                    ({ratingNo})
                  </Typography>
            </Grid>
        </Grid>
      </CardActionArea>
    </Card>
  );
}
