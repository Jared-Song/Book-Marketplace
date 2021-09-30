import React from "react";
import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import MyCard from "../../src/components/layouts/Card";
import styles from "../../styles/Home.module.css";
import TextField from "@material-ui/core/TextField";
import { Button } from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import SaveIcon from "@material-ui/icons/Save";
import Container from "@material-ui/core/Container";
import Link from "@material-ui/core/Link";
import axios from "axios";
import { useSnackbar } from "notistack";
import Router from "next/router";

const useStyles = makeStyles((theme) => ({
  root: {
    maxWidth: 600,
    margin: "65px 0px",
  },
  title: {
    borderBottom: "1px solid #eaeaea",
    // marginBottom: theme.spacing(2),
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
  confirmPassword: yup.string().required(),
  password: yup.string().required(),
  fullName: yup.string().required()
});

export default function SignUp() {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();

  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      username: "",
      fullName: "",
      address: "",
      phone: "",
      password: "",
      confirmPassword: "",
    },
    // resolver: yupResolver(SignupSchema),
  });

  const onSubmit = (data) => {
    console.log(data);
    axios
      .post(`/api/signup`, data)
      .then((res) => {
        if (res.status == 200) {
          // alert("Backend not connected!!");
          enqueueSnackbar("Welcome!", {
            variant: "success",
          });
          Router.push("/login");
        }
      })
      .catch((error) => {
        enqueueSnackbar("Something is wrong!!", {
          variant: "error",
        });
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
            Create a New Account
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
                  <Typography variant="subtitle1">Full Name</Typography>
                  <Controller
                    name="fullName"
                    control={control}
                    defaultValue=""
                    render={({ field }) => (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                      />
                    )}
                  />
                </Grid>
                <Grid item xs={12} container>
                  <Typography variant="subtitle1">Address</Typography>
                  <Controller
                    name="address"
                    control={control}
                    defaultValue=""
                    render={({ field }) => (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                        // label="Address"
                      />
                    )}
                  />
                </Grid>
                <Grid item xs={12} container>
                  <Typography variant="subtitle1">Phone</Typography>
                  <Controller
                    name="phone"
                    control={control}
                    defaultValue=""
                    render={({ field }) => (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                        // label="Phone"
                      />
                    )}
                  />
                </Grid>
                <Grid item xs={12} container>
                  <Typography variant="subtitle1">Password</Typography>
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
                      />
                    )}
                  />
                </Grid>
                <Grid item xs={12} container>
                  <Typography variant="subtitle1">Re-enter Password</Typography>
                  <Controller
                    name="confirmPassword"
                    control={control}
                    defaultValue=""
                    render={({ field }) => (
                      <TextField
                        {...field}
                        type="password"
                        variant="outlined"
                        fullWidth
                        margin="dense"
                        // label="Password"
                      />
                    )}
                  />
                </Grid>
                <Grid
                  item
                  xs={12}
                  container
                  direction="row"
                  justifyContent="space-around"
                  alignItems="center"
                >
                  <Button
                    variant="contained"
                    type="submit"
                    startIcon={<SaveIcon />}
                  >
                    Create
                  </Button>
                  <Button
                    variant="contained"
                    color="secondary"
                    onClick={() => {
                      reset();
                    }}
                  >
                    Reset
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
                  <Typography className={classes.text}>
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