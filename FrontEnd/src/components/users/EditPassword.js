import { Button, Grid, TextField, Typography } from "@material-ui/core";
import React from "react";
import { Controller, useForm } from "react-hook-form";
import { makeStyles } from "@material-ui/core/styles";
import { useSnackbar } from "notistack";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
  inputContainer: {
    display: 'flex',
    width: 500,
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

export default function EditPassword({ token, user }) {
  const { control, handleSubmit } = useForm();
  const { enqueueSnackbar } = useSnackbar();
  const classes = useStyles();

  const onSubmit = async ({password, rePassword}) => {
    console.log(password, rePassword)
    if (password !== rePassword) {
        enqueueSnackbar("Your password is different!", {
          variant: "error",
        });
        return
    }
    try {
      const {status} = await axios.post(process.env.NEXT_PUBLIC_EDIT_USER_URL + user.id, {
        ...user,
        password
      }, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        enqueueSnackbar(`Your account information has been updated`, {
        variant: "success",
      });
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
          <Typography variant="h4">Reset your password</Typography>
        </Grid>
        <Grid item xs={12}>
          <Grid
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
          >
            <Grid item className={classes.inputContainer}>
              <Typography className={classes.inputLabel}>New Password</Typography>
              <Controller
                name="password"
                control={control}
                render={({ field }) => {
                  return (
                    <TextField
                      {...field}
                      type="password"
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
              <Typography className={classes.inputLabel}>Re-enter password</Typography>
              <Controller
                name="rePassword"
                control={control}
                render={({ field }) => {
                  return (
                    <TextField
                      {...field}
                      type="password"
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
                RESET
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </form>
  );
}
