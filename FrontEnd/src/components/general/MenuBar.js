import React from "react";
import { alpha, makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import { useRouter } from "next/router";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import InputBase from "@material-ui/core/InputBase";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import SearchIcon from "@material-ui/icons/Search";
import AccountCircle from "@material-ui/icons/AccountCircle";
import { Button } from "@material-ui/core";
import Router from "next/router";
import _ from "lodash";
import { useCurrentUser } from "../../context/AuthContext";
import axios from "axios";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import Grid from "@material-ui/core/Grid";
import ShoppingCart from "./ShoppingCart";

const useStyles = makeStyles((theme) => ({
  grow: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    display: "none",
    [theme.breakpoints.up("sm")]: {
      display: "block",
    },
  },
  search: {
    position: "relative",
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    "&:hover": {
      backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    marginLeft: theme.spacing(2),
    marginRight: theme.spacing(2),

    // marginLeft: 0,
    width: "100%",
    [theme.breakpoints.up("sm")]: {
      width: "auto",
    },
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: "100%",
    position: "absolute",
    pointerEvents: "none",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  inputRoot: {
    color: "inherit",
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
    transition: theme.transitions.create("width"),
    width: "100%",
    [theme.breakpoints.up("md")]: {
      width: "40ch",
    },
  },
  sectionDesktop: {
    display: "flex",
  },
}));

export default function PrimarySearchAppBar() {
  const classes = useStyles();
  const { currentUser, loading, setToken } = useCurrentUser();
  const { query } = useRouter();
  const { keyword, filter } = query;
  const [searchFilter, setSearchFilter] = React.useState(filter || "title");
  const [searchKeywords, setSearchKeywords] = React.useState(keyword || "");
  const router = useRouter();
  const menuId = "primary-search-account-menu";

  const renderSearch = () => {
    return (
      <Grid
        container
        direction="row"
        justifyContent="flex-start"
        alignItems="center"
      >
        <Grid item>
          <FormControl variant="standard" size="small">
            <Select
              value={searchFilter}
              onChange={(event) => {
                setSearchFilter(event.target.value);
              }}
            >
              <MenuItem value="title">Title</MenuItem>
              <MenuItem value="authorName">Author</MenuItem>
              <MenuItem value="category">Category</MenuItem>
              <MenuItem value="isbn">ISBN</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item>
          <div className={classes.search}>
            <div className={classes.searchIcon}>
              <SearchIcon />
            </div>
            <InputBase
              value={searchKeywords}
              onChange={(event) => {
                setSearchKeywords(event.target.value);
              }}
              placeholder="Search…"
              classes={{
                root: classes.inputRoot,
                input: classes.inputInput,
              }}
              inputProps={{ "aria-label": "search" }}
            />
          </div>
        </Grid>
        <Grid item>
          <Button
            variant="outlined"
            onClick={() => {
              router.push({
                pathname: "/book/search",
                query: { keyword: searchKeywords, filter: searchFilter },
              });
            }}
          >
            Search
          </Button>
        </Grid>
      </Grid>
    );
  };

  return (
    <div className={classes.grow}>
      <AppBar position="static">
        <Toolbar>
          <Button
            onClick={() => {
              Router.push("/");
            }}
          >
            <Typography className={classes.title} variant="h6" noWrap>
              BOOKEROO
            </Typography>
          </Button>
          <div className={classes.grow} />
          <div className={classes.sectionDesktop}>
            {renderSearch()}

            <ShoppingCart />
            {!currentUser && !loading && (
              <>
                <Button
                  id="login-btn"
                  onClick={() => {
                    Router.push("/login");
                  }}
                >
                  login
                </Button>
                <Button
                  id="sign-up-btn"
                  onClick={() => {
                    Router.push("/signup");
                  }}
                >
                  signup
                </Button>
              </>
            )}

            {currentUser && !loading && (
              <>
                <IconButton
                  // edge="end"
                  onClick={() => {
                    Router.push("/account");
                  }}
                  color="inherit"
                >
                  <AccountCircle />
                </IconButton>
                <Button
                  style={{ whiteSpace: "nowrap" }}
                  onClick={() => {
                    setToken("");
                    axios
                      .get("/api/logout")
                      .then((_data) => {
                        window.location.href = "/";
                      })
                      .catch((error) => {
                        console.log(error);
                      });
                  }}
                >
                  Log out
                </Button>
              </>
            )}
          </div>
        </Toolbar>
      </AppBar>
    </div>
  );
}
