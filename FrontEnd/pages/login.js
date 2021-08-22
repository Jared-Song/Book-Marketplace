import React from "react";
import { useForm, Controller } from "react-hook-form";
import * as yup from "yup";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import MyCard from "../src/components/container/Card";
import styles from "../styles/Home.module.css";
import TextField from "@material-ui/core/TextField";
import { Button } from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import SaveIcon from "@material-ui/icons/Save";
import Container from "@material-ui/core/Container";
import Link from "@material-ui/core/Link";
import { yupResolver } from "@hookform/resolvers/yup";
import TwitterIcon from "@material-ui/icons/Twitter";
import axios from "axios";
import { useSnackbar } from "notistack";
import Router from "next/router";

const useStyles = makeStyles((theme) => ({
  root: {
    maxWidth: 600,
  },
  title: {
    borderBottom: "1px solid #eaeaea",
    paddingBottom: 25,
  },
  form: {
    margin: "10px 50px",
  },
  text: {
    marginRight: 10,
  },
  btmtext: {
    marginTop: 15,
  },
}));

const SignupSchema = yup.object().shape({
  username: yup.string().required(),
  address: yup.string().required(),
  phone: yup.string().required(),
  repassword: yup.string().required(),
  password: yup.string().required(),
});

export default function login() {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
  
    // resolver: yupResolver(SignupSchema),
  });
  const onSubmit = (data) => {
    axios
      .post(`/api/login`, data)
      .then((res) => {
        if (res.status == 200) {
          alert("Backend not connected!!");
          enqueueSnackbar("Welcome!", {
            variant: "success",
          });
          Router.push("/account");
        }
      })
      .catch((error) => {
        enqueueSnackbar("ID or password is invalid, please try again!"),
          {
            variant: "error",
          };
      });
  };

  return (
    <>
      <div className={classes.root}>
        <div className={classes.title}>
          <Typography
            variant="h5"
            fontWeight="fontWeightBold"
            className={styles.title}
          >
            Login
          </Typography>
        </div>

        <MyCard>
          <form onSubmit={handleSubmit(onSubmit)} className={classes.form}>
            <Container maxWidth="sm" className={classes.container}>
              <Grid container spacing={1}>
                <Grid item xs={12} container>
                  <Typography variant="subtitle1">User Name</Typography>
                  <Controller
                    name="username"
                    control={control}
                    defaultValue=""
                    render={({ field }) => (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                        // label="User Name"
                      />
                    )}
                  />
                </Grid>

                <Grid item xs={12} container>
                  <Typography variant="subtitle1">Passowrd</Typography>
                  <Controller
                    name="password"
                    control={control}
                    defaultValue=""
                    render={({ field }) => (
                      <TextField
                        {...field}
                        type="password"
                        variant="outlined"
                        fullWidth
                        margin="dense"
                        // label="Passowrd"
                      />
                    )}
                  />
                </Grid>

                <Grid
                  item
                  xs={12}
                  container
                  direction="column"
                  justifyContent="space-around"
                  alignItems="center"
                  // spacing={6}
                >
                  <Button
                    variant="contained"
                    type="submit"
                    startIcon={<SaveIcon />}
                  >
                    Login
                  </Button>
                  <br />
                  <Button startIcon={<TwitterIcon />}>
                    Login in with Twitter
                  </Button>
                </Grid>
                <Grid
                  item
                  xs={12}
                  container
                  justifyContent="center"
                  alignItems="center"
                  className={classes.btmtext}
                >
                  <Typography className={classes.text} variant="h6">
                    Already have an account?
                  </Typography>
                  <Link href="#">Sign in</Link>
                </Grid>
              </Grid>
            </Container>
          </form>
        </MyCard>
      </div>
    </>
  );
}
