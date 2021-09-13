import { Button, Grid, TextField, Typography } from "@material-ui/core";
import React from "react";
import { Controller, useForm } from "react-hook-form";
import { makeStyles } from "@material-ui/core/styles";
import { useSnackbar } from "notistack";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
  inputContainer: {
    display: 'flex',
    width: 400,
    justifyContent: 'space-between',
  },
  inputLabel: {
    position: 'relative',
    top: 15,
    whiteSpace: 'nowrap',
    paddingRight: theme.spacing(4),
  },
  inputField: {
    width: 250
  },
  buttonContainer: {
    paddingTop: theme.spacing(5)
  }
}));

export default function EditAccountInformation({ user, token, refetch }) {
  const { control, handleSubmit } = useForm({
    defaultValues: user,
  });
  const { enqueueSnackbar } = useSnackbar();
  const classes = useStyles();

  const onSubmit = async (data) => {
    try {
      const {status} = await axios.post(process.env.NEXT_PUBLIC_EDIT_USER_URL + user.id, data, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        enqueueSnackbar(`Your account information has been updated`, {
        variant: "success",
      });
      refetch()
    } catch (error) {
      enqueueSnackbar("Something is wrong when edit!!", {
        variant: "error",
      });
    }
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography variant="h4">Edit your account information</Typography>
        </Grid>
        <Grid item xs={12}>
          <Grid
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
          >
            <Grid item className={classes.inputContainer}>
              <Typography className={classes.inputLabel}>User Name</Typography>
              <Controller
                name="username"
                control={control}
                render={({ field }) => {
                  return (
                    <TextField
                      {...field}
                      className={classes.inputField}
                      variant="outlined"
                      disabled
                      fullWidth
                      margin="dense"
                    />
                  );
                }}
              />
            </Grid>
            <Grid item className={classes.inputContainer}>
              <Typography className={classes.inputLabel}>Full Name</Typography>
              <Controller
                name="fullName"
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
            <Grid item className={classes.inputContainer}>
              <Typography className={classes.inputLabel}>Email</Typography>
              <Controller
                name="email"
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
            <Grid item className={classes.inputContainer}>
              <Typography className={classes.inputLabel}>Address</Typography>
              <Controller
                name="address"
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
              <Button variant="contained" color="primary" size="large" type="submit">
                SAVE
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </form>
  );
}
