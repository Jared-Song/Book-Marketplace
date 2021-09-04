import React from "react";
import Fab from "@material-ui/core/Fab";
import AddIcon from "@material-ui/icons/Add";
import { makeStyles } from "@material-ui/core/styles";
import { useSnackbar } from "notistack";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import ListItemText from "@material-ui/core/ListItemText";
import ListItem from "@material-ui/core/ListItem";
import List from "@material-ui/core/List";
import Divider from "@material-ui/core/Divider";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import CloseIcon from "@material-ui/icons/Close";
import Slide from "@material-ui/core/Slide";
import { Controller, useForm } from "react-hook-form";
import { Box, Grid, TextField } from "@material-ui/core";
import axios from "axios";

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
}));

export default function CreateBook({ token, refetch }) {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const { enqueueSnackbar } = useSnackbar();

  const { control, handleSubmit } = useForm({
    defaultValues: {
      title: "",
      authorFirstName: "",
      authorLastName: "",
      isbn: "",
      quantity: 0,
      imageUrl: "",
      price: 0,
    },
  });

  const onCreateBook = async (data) => {
    try {
      const { status } = await axios.post(
        process.env.NEXT_PUBLIC_BOOK_URL + "new",
        data,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (status === 201) {
        enqueueSnackbar("Book created!", {
          variant: "success",
        });
        refetch();
        setOpen(false);
      } else {
        throw "Unable to create book";
      }
    } catch (error) {
      enqueueSnackbar("Something is wrong!!", {
        variant: "error",
      });
    }
  };

  return (
    <>
      <Fab
        size="medium"
        color="primary"
        aria-label="add"
        onClick={() => {
          setOpen(true);
        }}
      >
        <AddIcon />
      </Fab>
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
        <form onSubmit={handleSubmit(onCreateBook)}>
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
                Create Book
              </Typography>
              <Button autoFocus color="inherit" type="submit">
                Save
              </Button>
            </Toolbar>
          </AppBar>
          <Grid container spacing={2} className={classes.formContainer}>
            <Grid item xs={12}>
              <Typography variant="subtitle1">Title</Typography>
              <Controller
                name="title"
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
    </>
  );
}
