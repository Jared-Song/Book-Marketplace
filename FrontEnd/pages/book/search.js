import _ from "lodash";
import React from "react";
import useAxios from "axios-hooks";
import { useRouter } from "next/router";

//Components
import BigMenu from "../../src/components/general/BigMenu";
import BookCard from "../../src/components/general/BookCard";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";

//MUI
import FormControl from "@material-ui/core/FormControl";
import Grid from "@material-ui/core/Grid";
import makeStyles from "@material-ui/core/styles/makeStyles";
import MenuItem from "@material-ui/core/MenuItem";
import Select from "@material-ui/core/Select";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
  list: {
    padding: theme.spacing(2),
    direction: "row",
    justifyContent: "space-around",
    alignItems: "left",
  },
  filterAndSortContainer: {
    marginTop: 40,
    paddingLeft: 40,
    display: "flex",
  },
}));

export default function Search() {
  const { query } = useRouter();
  const { keyword, filter } = query;

  const [url, setUrl] = React.useState(null);
  const [{ data, loading, error }, refetch] = useAxios(url);
  const classes = useStyles();
  const [order, setOrder] = React.useState("alphabetical");
  const [conditionFilter, setConditionFilter] = React.useState("all");

  React.useEffect(() => {
    if (keyword && filter) {
      if (conditionFilter === "all") {
        setUrl(process.env.NEXT_PUBLIC_BROWSE_URL + filter + "/" + keyword);
      } else {
        setUrl(
          process.env.NEXT_PUBLIC_BROWSE_URL +
            "filter/" +
            conditionFilter +
            "/" +
            filter +
            "/" +
            keyword
        );
      }
    }
  }, [keyword, filter, conditionFilter]);

  const renderContent = () => {
    if (data && data.length > 0) {
      let books;
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

  if (loading || !url) {
    return (
      <Grid container>
        <Grid item xs={2}>
          <BigMenu />
        </Grid>
        <Grid item xs={10}>
          <SimpleLoadingPlaceholder />
        </Grid>
      </Grid>
    );
  }

  return (
    <Grid container>
      <Grid item xs={2}>
        <BigMenu />
      </Grid>
      <Grid item xs={10}>
        <Grid container spacing={2}>
          <Grid item xs={4} className={classes.filterAndSortContainer}>
            <Typography
              style={{
                whiteSpace: "nowrap",
                position: "relative",
                top: 8,
                paddingRight: 8,
              }}
            >
              {" "}
              Sort Order{" "}
            </Typography>
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
          <Grid item xs={4} className={classes.filterAndSortContainer}>
            <Typography
              style={{
                whiteSpace: "nowrap",
                position: "relative",
                top: 8,
                paddingRight: 8,
              }}
            >
              {" "}
              Filter{" "}
            </Typography>
            <FormControl variant="outlined" size="small" fullWidth>
              <Select
                labelId="order-select-label"
                defaultValue="all"
                value={conditionFilter}
                onChange={(event) => {
                  setConditionFilter(event.target.value);
                }}
              >
                <MenuItem value="all">All</MenuItem>
                <MenuItem value="new">New</MenuItem>
                <MenuItem value="used">Used</MenuItem>
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
