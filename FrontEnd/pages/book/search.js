import React from "react";
import withSession from "../../src/lib/session";
import { useRouter } from "next/router";
import useAxios from "axios-hooks";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import BigMenu from "../../src/components/general/BigMenu";
import { makeStyles } from "@material-ui/core/styles";
import BookCard from "../../src/components/general/BookCard";
import ArrowUpwardIcon from "@material-ui/icons/ArrowUpward";
import ArrowDownwardIcon from "@material-ui/icons/ArrowDownward";
import Select from "@material-ui/core/Select";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";
import MenuItem from "@material-ui/core/MenuItem";
import _ from "lodash"

const useStyles = makeStyles((theme) => ({
  list: {
    padding: theme.spacing(2),
    direction: "row",
    justifyContent: "space-around",
    alignItems: "left",
  },
}));

export default function Search() {
  const { query } = useRouter();
  const { keyword, filter } = query;
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_BROWSE_URL + filter + "/" + keyword
  );
  const classes = useStyles();
  const [order, setOrder] = React.useState('alphabetical');

  const renderContent = () => {
    if (data && data.length > 0) {
      let books
      switch (order) {
        case "priceDesc":
          books = _.orderBy(data, ["price"], ["desc"]);
          break;
        case "priceAsc":
          books = _.orderBy(data, ["price"], ["asc"]);
          break;
        case "alphabetical":
          books = data;
          break;
      }
      return (
        <>
          {books.map((book) => {
            return <BookCard key={book.id} book={book} />;
          })}
        </>
      );
    }
    return <Typography variant="h2">No result found!</Typography>;
  };

  return (
    <Grid container>
      <Grid item xs={2}>
        <BigMenu />
      </Grid>
      <Grid item xs={10}>
        <Grid container spacing={2}>
          <Grid item xs={4} style={{ marginTop: 40, paddingLeft: 40, display: "flex" }}>
            <Typography style={{whiteSpace: "nowrap", position: "relative", top: 8, paddingRight: 8}}> Sort Order </Typography>
            <FormControl variant="outlined" size="small" fullWidth>
              <Select
                labelId="order-select-label"
                placeholder="Sort Order"
                value={order}
                onChange={(event) => {
                  setOrder(event.target.value);
                }}
              >
                <MenuItem value="priceDesc">Price High to Low</MenuItem>
                <MenuItem value="priceAsc">Price Low to High</MenuItem>
                <MenuItem value="alphabetical">Alphabetical</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={12}>
            <Grid container className={classes.list}>
              {renderContent()}
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
}
