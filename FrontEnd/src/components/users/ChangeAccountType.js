import { Button, Grid, TextField, Typography } from "@material-ui/core";
import React from "react";
import { Controller, useForm } from "react-hook-form";
import { makeStyles } from "@material-ui/core/styles";
import { useSnackbar } from "notistack";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
  inputContainer: {
    display: "flex",
    width: 500,
    justifyContent: "space-between",
  },
  inputLabel: {
    position: "relative",
    top: 15,
    whiteSpace: "nowrap",
    paddingRight: theme.spacing(4),
  },
  inputField: {
    width: 250,
  },
  buttonContainer: {
    paddingTop: theme.spacing(5),
  },
}));

export default function ChangeAccountType({ token, user }) {
  const { control, handleSubmit } = useForm();
  const { enqueueSnackbar } = useSnackbar();
  const classes = useStyles();

  const onSubmit = async ({ companyName, abn }) => {
    if (!companyName || !abn) {
      enqueueSnackbar("Company Name or ABN can't be empty", {
        variant: "error",
      });
      return;
    }
    try {
      const { status } = await axios.post(
        process.env.NEXT_PUBLIC_EDIT_USER_URL + user.id,
        {
          ...user,
          business,
          abn,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      enqueueSnackbar(`Your request has been submitted`, {
        variant: "success",
      });
    } catch (error) {
      enqueueSnackbar("Something is wrong!!", {
        variant: "error",
      });
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography variant="h4">Change User Type</Typography>
        </Grid>
        <Grid item xs={12}>
          <Grid
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
          >
            <Grid item className={classes.inputContainer}>
              <Typography className={classes.inputLabel}>
                Company Name
              </Typography>
              <Controller
                name="companyName"
                control={control}
                render={({ field }) => {
                  return (
                    <TextField
                      {...field}
                      className={classes.inputField}
                      variant="outlined"
                      fullWidth
                      margin="dense"
                    />
                  );
                }}
              />
            </Grid>
            <Grid item className={classes.inputContainer}>
              <Typography className={classes.inputLabel}>ABN</Typography>
              <Controller
                name="abn"
                control={control}
                render={({ field }) => {
                  return (
                    <TextField
                      {...field}
                      variant="outlined"
                      className={classes.inputField}
                      fullWidth
                      margin="dense"
                    />
                  );
                }}
              />
            </Grid>
            <Grid item className={classes.buttonContainer}>
              <Button
                variant="contained"
                color="primary"
                size="large"
                type="submit"
              >
                submit request
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </form>
  );
}
