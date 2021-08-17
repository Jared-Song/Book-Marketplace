import React from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import Box from "@material-ui/core/Box";
import { makeStyles, useTheme } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import MyCard from "../src/components/container/Card";
import styles from "../styles/Home.module.css";
import clsx from "clsx";
import Divider from '@material-ui/core/Divider';

const useStyles = makeStyles((theme) => ({
  root: {
    height: 300,
  },
  title:{
    borderBottom: "1px solid #eaeaea",
    marginBottom: theme.spacing(2)
  }
}));

const SignupSchema = yup.object().shape({
  firstName: yup.string().required(),
  age: yup.number().required().positive().integer(),
  website: yup.string().url(),
});

export default function SignIn() {
  const classes = useStyles();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(SignupSchema),
  });
  const onSubmit = (data) => {
    alert(JSON.stringify(data));
  };

  return (
    <>
      <div className={clsx(classes.root, styles.container)}>
        <div className={styles.title}>
          <Typography variant="h5" fontWeight="fontWeightBold">
            Create a New Account
          </Typography>
        </div>
       
        <MyCard>
          <form onSubmit={handleSubmit(onSubmit)}>
            <div>
              <label>First Name</label>
              <input {...register("firstName")} />
              {errors.firstName && <p>{errors.firstName.message}</p>}
            </div>
            <div style={{ marginBottom: 10 }}>
              <label>Last Name</label>
              <input {...register("lastName")} />
              {errors.lastName && <p>{errors.lastName.message}</p>}
            </div>
            <div>
              <label>Age</label>
              <input
                type="number"
                {...register("age", { valueAsNumber: true })}
              />
              {errors.age && <p>{errors.age.message}</p>}
            </div>
            <div>
              <label>Website</label>
              <input {...register("website")} />
              {errors.website && <p>{errors.website.message}</p>}
            </div>
            <input type="submit" />
          </form>
        </MyCard>
      </div>
    </>
  );
}
