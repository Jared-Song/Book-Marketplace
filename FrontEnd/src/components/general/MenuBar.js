import React from "react";
import { alpha, makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import InputBase from "@material-ui/core/InputBase";
import Badge from "@material-ui/core/Badge";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import SearchIcon from "@material-ui/icons/Search";
import AccountCircle from "@material-ui/icons/AccountCircle";
import ShoppingCartIcon from "@material-ui/icons/ShoppingCart";
import { Button } from "@material-ui/core";
import Router from "next/router";
import _ from "lodash";
import { useCurrentUser } from "../../context/AuthContext";
import axios from "axios";

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
    marginRight: theme.spacing(2),
    marginLeft: 0,
    width: "100%",
    [theme.breakpoints.up("sm")]: {
      marginLeft: theme.spacing(3),
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
      width: "20ch",
    },
  },
  sectionDesktop: {
    display: "flex",
  },
}));

export default function PrimarySearchAppBar() {
  const classes = useStyles();
  const { currentUser, loading, setToken } = useCurrentUser();
  
  const menuId = "primary-search-account-menu";

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
          <div className={classes.search}>
            <div className={classes.searchIcon}>
              <SearchIcon />
            </div>
            <InputBase
              placeholder="Search…"
              classes={{
                root: classes.inputRoot,
                input: classes.inputInput,
              }}
              inputProps={{ "aria-label": "search" }}
            />
          </div>
          <div className={classes.grow} />
          <div className={classes.sectionDesktop}>
            {/* <IconButton color="inherit">
              <Badge badgeContent={4} color="secondary">
                <ShoppingCartIcon />
              </Badge>
            </IconButton> */}

            {!currentUser && !loading && (
              <>
                <Button
                  onClick={() => {
                    Router.push("/login");
                  }}
                >
                  login
                </Button>
                <Button
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
                  onClick={() => {
                    setToken("")
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
