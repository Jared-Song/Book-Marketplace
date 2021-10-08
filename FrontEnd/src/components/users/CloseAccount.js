import { Button, Typography } from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import React from "react";
import { useSnackbar } from "notistack";
import axios from "axios";
import { useCurrentUser } from "../../context/AuthContext";

export default function CloseAccount({ user, token }) {
  const { enqueueSnackbar } = useSnackbar();
  const { setToken } = useCurrentUser();
  const onCloseAccount = async () => {
    try {
      const result = await axios.post(
        process.env.NEXT_PUBLIC_EDIT_USER_URL + "userStatus/" + user.id,
        {
          userStatus: "TERMINATED",
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      enqueueSnackbar(`Your account has been closed!`, {
        variant: "success",
      });
      setToken("");
      await axios.get("/api/logout");
      window.location.href = "/";
    } catch (error) {
      console.log(error);
      enqueueSnackbar("Something is wrong when edit!!", {
        variant: "error",
      });
    }
  };

  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Typography variant="h4">Close Account</Typography>
      </Grid>
      <Grid item xs={12}>
        <Grid
          container
          direction="column"
          justifyContent="center"
          alignItems="center"
        >
          <Grid item>
            <Button
              variant="contained"
              style={{ background: "red", color: "white" }}
              size="large"
              onClick={onCloseAccount}
            >
              Close Account
            </Button>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
}
