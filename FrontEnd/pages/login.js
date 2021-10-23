import * as yup from "yup";
import axios from "axios";
import React from "react";
import Router from "next/router";
import styles from "../styles/Home.module.css";
import { useForm, Controller } from "react-hook-form";
import { useSnackbar } from "notistack";
import { yupResolver } from "@hookform/resolvers/yup";

//Components
import MyCard from "../src/components/layouts/Card";

//MUI
import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";
import FormHelperText from "@material-ui/core/FormHelperText";
import Grid from "@material-ui/core/Grid";
import Link from "@material-ui/core/Link";
import makeStyles from "@material-ui/core/styles/makeStyles";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";

//Icons
import SaveIcon from "@material-ui/icons/Save";
import TwitterIcon from "@material-ui/icons/Twitter";

//Context
import { useCurrentUser } from "../src/context/AuthContext";

const useStyles = makeStyles((theme) => ({
  root: {
    maxWidth: 600,
    margin: "65px 0px",
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

const LoginSchema = yup.object().shape({
  username: yup.string().required(),
  password: yup.string().required(),
});

export default function Login() {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();
  const { setToken, currentUser} = useCurrentUser();

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(LoginSchema),
  });

  React.useEffect(() => {
    if(currentUser?.role) {
      switch(currentUser.role){
        case "ADMIN":
          Router.push("/admin/requests");
          break;
        case "USER_NORMAL":
          Router.push("/account");
          break;
        case "USER_BUSINESS":
          Router.push("/account");
          break;
      }
    }
  }, [currentUser])

  const onSubmit = (data) => {
    axios
      .post(`/api/login`, data)
      .then((res) => {
        if (res.status == 200) {
          setToken(res.data?.token);
          enqueueSnackbar("Welcome!", {
            variant: "success",
          });
        }
      })
      .catch((error) => {
        console.log(error)
        enqueueSnackbar("ID or password is invalid, please try again!", {
          variant: "error",
        });
      });
  };

  return (
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
                      error={errors.username}
                      variant="outlined"
                      fullWidth
                      margin="dense"
                    />
                  )}
                />
                {errors.username?.message && (
                  <FormHelperText
                    style={{ color: "red" }}
                    id="component-error-text"
                  >
                    {errors.username.message}
                  </FormHelperText>
                )}
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
                      error={errors.password}
                      variant="outlined"
                      fullWidth
                      margin="dense"
                    />
                  )}
                />
                {errors.password?.message && (
                  <FormHelperText
                    style={{ color: "red" }}
                    id="component-error-text"
                  >
                    {errors.password.message}
                  </FormHelperText>
                )}
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
                <Button startIcon={<TwitterIcon />} onClick={()=>{
                  alert("Sorry, this function is currently not availabe!")
                }}>
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
                  Don't have an account?
                </Typography>
                <Link id="sign-up" href="/signup">Create one now!</Link>
              </Grid>
            </Grid>
          </Container>
        </form>
      </MyCard>
    </div>
  );
}
