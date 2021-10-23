import IconButton from "@material-ui/core/IconButton";
import Badge from "@material-ui/core/Badge";
import { makeStyles } from "@material-ui/core/styles";
import ShoppingCartIcon from "@material-ui/icons/ShoppingCart";
import TextField from "@material-ui/core/TextField";
import React from "react";
import Drawer from "@material-ui/core/Drawer";
import axios from "axios";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import { Box, Typography } from "@material-ui/core";
import DeleteIcon from "@material-ui/icons/Delete";
import { useShoppingCart } from "../../context/ShoppingCartContext";
import _ from "lodash";
import { useCurrentUser } from "../../context/AuthContext";
import { useSnackbar } from "notistack";
import { PayPalScriptProvider, PayPalButtons } from "@paypal/react-paypal-js";
import Link from "@material-ui/core/Link";
import Router from "next/router";

// TEST Account
// sb-a4uf38058585@personal.example.com
// Password
// X|8rc^4_

const useStyles = makeStyles((theme) => ({
  cartContainer: {
    width: 500,
    padding: theme.spacing(5),
  },
  cartButton: {
    width: 200,
    borderRadius: 14,
  },
}));

const initialOptions = {
  "client-id": process.env.NEXT_PUBLIC_PAYPAL_CLIENT_ID,
  currency: "AUD",
};

export default function ShoppingCart() {
  const [open, setOpen] = React.useState(false);
  const classes = useStyles();
  const [loading, setIsLoading] = React.useState(false);
  console.log(initialOptions);
  const {
    cartItems,
    addIntoShoppingCart,
    editShoppingCartItem,
    removeShoppingCartItem,
    removeAllItems,
  } = useShoppingCart();
  const { enqueueSnackbar } = useSnackbar();
  const { token, currentUser } = useCurrentUser();
  const getCountCartItems = () => {
    const countCartItems = cartItems.length;
    return countCartItems.toString();
  };
  const onCheckOut = async () => {
    try {
      setIsLoading(true);
      await Promise.all(
        cartItems.map(async (item) => {
          await axios.post(process.env.NEXT_PUBLIC_TRANSACTION_URL + "new", {
            bookID: item.id,
            buyerID: parseInt(currentUser.id),
            quantity: item.quantity,
            price: item.price * item.quantity,
            status: "PROCESSING",
          });
          enqueueSnackbar("Check out success!", {
            variant: "success",
          });
        })
      );
      removeAllItems();
      setIsLoading(false);
      setOpen(false);
    } catch (error) {
      console.log(error);
      enqueueSnackbar("Something is wrong when checkout!!", {
        variant: "error",
      });
    }
  };
  const renderCartContent = () => {
    console.log(
      _.sum(
        cartItems.map((item) => {
          return item.price * item.quantity;
        })
      )
    );
    return (
      <div className={classes.cartContainer}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Typography variant="h5">Shopping Cart</Typography>
          </Grid>
          <Grid item xs={12}>
            <Grid container spacing={1}>
              {cartItems.map((item, key) => {
                return (
                  <Grid item xs={12} key={item.id}>
                    <Grid container>
                      <Grid item xs={6}>
                        <Typography variant="body1">
                          {key + 1 + ". " + item.title}
                        </Typography>
                      </Grid>

                      <Grid item xs={2}>
                        {"$" + item.price}
                      </Grid>

                      <Grid item xs={2}>
                        <TextField
                          type="number"
                          style={{ width: 50 }}
                          value={item.quantity}
                          InputLabelProps={{
                            shrink: true,
                          }}
                          InputProps={{ inputProps: { min: 1 } }}
                          onChange={(e) => {
                            editShoppingCartItem({
                              ...item,
                              quantity: e.target.value,
                            });
                          }}
                        />
                      </Grid>
                      <Grid item xs={2}>
                        <IconButton
                          size="small"
                          onClick={() => {
                            removeShoppingCartItem(item.id);
                          }}
                        >
                          <DeleteIcon />
                        </IconButton>
                      </Grid>
                    </Grid>
                  </Grid>
                );
              })}
            </Grid>
            <Grid style={{ paddingTop: 40 }}>
              <Typography variant="body1">
                Total Amount: $
                {_.sum(
                  cartItems.map((item) => {
                    return item.price * item.quantity;
                  })
                )}
              </Typography>
            </Grid>
          </Grid>
          {cartItems.length > 0 && currentUser && currentUser.role != "ADMIN" && (
            <Grid item xs={12} style={{ paddingTop: 20 }}>
              <PayPalScriptProvider options={initialOptions}>
                <PayPalButtons
                  style={{ layout: "horizontal" }}
                  onApprove={(data, actions) => {
                    onCheckOut();
                  }}
                  createOrder={(data, actions) => {
                    return actions.order.create({
                      purchase_units: [
                        {
                          amount: {
                            value: _.sum(
                              cartItems.map((item) => {
                                return item.price * item.quantity;
                              })
                            ),
                          },
                        },
                      ],
                    });
                  }}
                />
              </PayPalScriptProvider>
            </Grid>
          )}
          {cartItems.length > 0 && !currentUser && (
            <Grid item xs={12}>
              <Button
                variant="contained"
                size="large"
                color="primary"
                onClick={() => {
                  setOpen(false);

                  Router.push("/login");

                }}
              >
                Login Now
              </Button>
            </Grid>
          )}
          {cartItems.length <= 0 && (
            <Grid item xs={12}>
              <Typography variant="h6">
                Please add your first book to the shopping Cart
              </Typography>
            </Grid>
          )}
        </Grid>
      </div>
    );
  };

  return (
    <React.Fragment>
      <IconButton
        color="inherit"
        onClick={() => {
          setOpen(true);
        }}
      >
        <Badge color="secondary" badgeContent={getCountCartItems()}>
          <ShoppingCartIcon />
        </Badge>
      </IconButton>
      <Drawer
        anchor="right"
        open={open}
        onClose={() => {
          setOpen(false);
        }}
      >
        {renderCartContent()}
      </Drawer>
    </React.Fragment>
  );
}
