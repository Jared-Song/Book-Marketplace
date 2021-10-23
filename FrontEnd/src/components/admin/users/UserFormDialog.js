import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import CloseIcon from "@material-ui/icons/Close";
import Slide from "@material-ui/core/Slide";
import { Controller, useForm } from "react-hook-form";
import { Grid, TextField, Select, MenuItem } from "@material-ui/core";

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const useStyles = makeStyles((theme) => ({
  appBar: {
    position: "relative",
  },
  title: {
    marginLeft: theme.spacing(2),
    flex: 1,
  },
  formContainer: {
    padding: theme.spacing(2),
  },
  dialog: {
    overflow: "hidden",
  },
  button: {
    margin: theme.spacing(1),
  },
  select: {
    position: "relative",
    height: 40,
    top: theme.spacing(1),
  },
}));

export default function UserFormDialog({
  user,
  open,
  title,
  setOpen,
  onSubmit,
}) {
  const classes = useStyles();
  const { control, handleSubmit } = useForm({
    defaultValues: {
      ...user,
      companyName: user.business && user.business.companyName,
      abn: user.business && user.business.abn,
    },
  });
  return (
    <Dialog
      open={open}
      maxWidth="sm"
      fullWidth
      classes={{ paper: classes.dialog }}
      onClose={() => {
        setOpen(false);
      }}
      TransitionComponent={Transition}
    >
      <form onSubmit={handleSubmit(onSubmit)}>
        <AppBar className={classes.appBar}>
          <Toolbar>
            <IconButton
              edge="start"
              color="inherit"
              onClick={() => {
                setOpen(false);
              }}
              aria-label="close"
            >
              <CloseIcon />
            </IconButton>
            <Typography variant="h6" className={classes.title}>
              {title}
            </Typography>
            <Button autoFocus color="inherit" type="submit">
              Save
            </Button>
          </Toolbar>
        </AppBar>
        <Grid container spacing={1} className={classes.formContainer}>
          <Grid item xs={6}>
            <Typography variant="subtitle1">User ID: {user.id}</Typography>
          </Grid>
          <Grid item xs={12}>
            <Grid container spacing={1}>
              <Grid item xs={6}>
                <Typography variant="subtitle1">User Name</Typography>

                <Controller
                  name="username"
                  control={control}
                  render={({ field }) => {
                    return (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                        disabled
                      />
                    );
                  }}
                />
              </Grid>
              <Grid item xs={6}>
                <Typography variant="subtitle1"> Full Name </Typography>
                <Controller
                  name="fullName"
                  control={control}
                  render={({ field }) => {
                    return (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                      />
                    );
                  }}
                />
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={6}>
            <Typography variant="subtitle1">Email</Typography>
            <Controller
              name="email"
              control={control}
              render={({ field }) => {
                return (
                  <TextField
                    {...field}
                    variant="outlined"
                    fullWidth
                    margin="dense"
                  />
                );
              }}
            />
          </Grid>
          {user.role === "USER_BUSINESS" && (
            <>
              <Grid item xs={6}>
                <Typography variant="subtitle1">Company Name</Typography>
                <Controller
                  name="companyName"
                  control={control}
                  render={({ field }) => {
                    return (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                      />
                    );
                  }}
                />
              </Grid>
              <Grid item xs={6}>
                <Typography variant="subtitle1">ABN</Typography>
                <Controller
                  name="abn"
                  control={control}
                  render={({ field }) => {
                    return (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                      />
                    );
                  }}
                />
              </Grid>
            </>
          )}
          <Grid item xs={6}>
            <Typography variant="subtitle1">Status</Typography>
            <Controller
              name="userStatus"
              control={control}
              render={({ field }) => {
                return (
                  <Select
                    name="userStatus"
                    fullWidth
                    size="small"
                    onChange={(event) => {
                      field.onChange(event.target.value);
                    }}
                    value={field.value}
                    className={classes.select}
                    variant="outlined"
                  >
                    <MenuItem value="ENABLED">Enabled</MenuItem>
                    <MenuItem value="DISABLED">Disabled</MenuItem>
                    <MenuItem value="SUSPENDED">Suspended</MenuItem>
                    <MenuItem value="TERMINATED">Terminated</MenuItem>
                    <MenuItem value="PENDING_REGISTRATION">
                      Pending Registration
                    </MenuItem>
                    <MenuItem value="DISABLED_REVIEWS_AND_REQUESTS">
                      Disabled Review and requests
                    </MenuItem>
                  </Select>
                );
              }}
            />
          </Grid>
          <Grid item xs={12}>
            <Typography variant="subtitle1">Address</Typography>
            <Controller
              name="address"
              control={control}
              render={({ field }) => {
                return (
                  <TextField
                    {...field}
                    variant="outlined"
                    fullWidth
                    margin="dense"
                  />
                );
              }}
            />
          </Grid>
        </Grid>
      </form>
    </Dialog>
  );
}
