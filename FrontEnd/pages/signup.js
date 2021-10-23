import * as yup from "yup";
import axios from "axios";
import React from "react";
import Router from "next/router";
import { toInteger } from "lodash";
import { useForm, Controller } from "react-hook-form";
import { useSnackbar } from "notistack";
import { yupResolver } from "@hookform/resolvers/yup";

//Components
import MyCard from "../src/components/layouts/Card";
import styles from "../styles/Home.module.css";

//MUI
import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Link from "@material-ui/core/Link";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Switch from "@material-ui/core/Switch";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";

//Icons
import SaveIcon from "@material-ui/icons/Save";

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

const SignupSchema = yup.object().shape({
  username: yup.string().required(),
  address: yup.string().required(),
  email: yup.string().required(),
  confirmPassword: yup.string().required(),
  password: yup.string().required(),
  fullName: yup.string().required(),
});

export default function SignUp() {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();
  const [isBusiness, setIsBusiness] = React.useState(false);

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
      companyName: "",
      abn: "",
      email: "",
      password: "",
      confirmPassword: "",
    },
    resolver: yupResolver(SignupSchema),
  });

  const onSubmit = (data) => {
    const createData =
      data.companyName && data.abn
        ? {
            ...data,
            business: {
              companyName: data.companyName,
              abn: toInteger(data.abn),
            },
            role: "USER_BUSINESS",
          }
        : {
            ...data,
            role: "USER_NORMAL",
          };
    console.log(createData);
    axios
      .post(`/api/signup`, createData)
      .then((res) => {
        if (res.status == 200) {
          enqueueSnackbar("Welcome!", {
            variant: "success",
          });
          Router.push("/login");
        }
      })
      .catch(({ response }) => {
        if (response && response.data) {
          const messages = Object.values(response.data);
          messages.forEach((element) => {
            enqueueSnackbar(element, {
              variant: "error",
            });
          });
        } else {
          enqueueSnackbar("Something is wrong!!", {
            variant: "error",
          });
        }
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
        <Grid
          container
          direction="row"
          justifyContent="center"
          alignItems="center"
        >
          <Typography>Create a Business Account</Typography>
          <Switch
            onChange={(event) => {
              setIsBusiness(event.target.checked);
            }}
            color="primary"
            name="checkedB"
          />
        </Grid>

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
                        error={errors.username}
                        margin="dense"
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
                        error={errors.fullName}
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
                        error={errors.address}
                        margin="dense"
                        // label="Address"
                      />
                    )}
                  />
                </Grid>
                {isBusiness && (
                  <>
                    <Grid item xs={12} container>
                      <Typography variant="subtitle1">Company Name</Typography>
                      <Controller
                        name="companyName"
                        control={control}
                        defaultValue=""
                        render={({ field }) => (
                          <TextField
                            {...field}
                            variant="outlined"
                            fullWidth
                            error={errors.companyName}
                            margin="dense"
                          />
                        )}
                      />
                    </Grid>
                    <Grid item xs={12} container>
                      <Typography variant="subtitle1">ABN</Typography>
                      <Controller
                        name="abn"
                        control={control}
                        defaultValue=""
                        render={({ field }) => (
                          <TextField
                            {...field}
                            variant="outlined"
                            error={errors.abn}
                            fullWidth
                            margin="dense"
                          />
                        )}
                      />
                    </Grid>
                  </>
                )}

                <Grid item xs={12} container>
                  <Typography variant="subtitle1">Email</Typography>
                  <Controller
                    name="email"
                    control={control}
                    defaultValue=""
                    render={({ field }) => (
                      <TextField
                        {...field}
                        variant="outlined"
                        error={errors.email}
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
                        error={errors.password}
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
                        error={errors.confirmPassword}
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
                  <Link href="/login">Login Now</Link>
                </Grid>
              </Grid>
            </Container>
          </form>
        </MyCard>
      </div>
    </>
  );
}
