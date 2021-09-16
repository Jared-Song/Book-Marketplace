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
import CloudUploadIcon from "@material-ui/icons/CloudUpload";
import { DropzoneDialog } from "material-ui-dropzone";
import readFileDataAsBase64 from "../../../util/ReadFileDataAsBase64";

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
}));

export default function BookFormDialog({
  existingBook,
  open,
  title,
  setOpen,
  onSubmit,
}) {
  const classes = useStyles();
  const { control, handleSubmit } = useForm({
    defaultValues: existingBook,
  });

  const [openUpload, setOpenUpload] = React.useState(false);

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
              {title || "Create Book"}
            </Typography>
            <Button autoFocus color="inherit" type="submit">
              Save
            </Button>
          </Toolbar>
        </AppBar>
        <Grid container spacing={1} className={classes.formContainer}>
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
                    placeholder="Book Tile"

                  />
                );
              }}
            />
          </Grid>
          <Grid item xs={12}>
            <Grid container spacing={1}>
              
              <Grid item xs={6}>
              <Typography variant="subtitle1">Author</Typography>

                <Controller
                  name="authorName"
                  control={control}
                  render={({ field }) => {
                    return (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                        placeholder="Author Name"
                      />
                    );
                  }}
                />
              </Grid>
              <Grid item xs={6}>
                <Typography variant="subtitle1">Category</Typography>
                <Controller
                  name="category"
                  control={control}
                  render={({ field }) => {
                    return (
                      <TextField
                        {...field}
                        variant="outlined"
                        fullWidth
                        margin="dense"
                        placeholder="Category"
                      />
                    );
                  }}
                />
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={6}>
            <Typography variant="subtitle1">ISBN</Typography>
            <Controller
              name="isbn"
              control={control}
              render={({ field }) => {
                return (
                  <TextField
                    {...field}
                    variant="outlined"
                    fullWidth
                    placeholder="ISBN"
                    margin="dense"
                  />
                );
              }}
            />
          </Grid>
          <Grid item xs={6}>
            <Typography variant="subtitle1">Quantity</Typography>
            <Controller
              name="quantity"
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
          <Grid item xs={4}>
            <Typography variant="subtitle1">Price</Typography>
            <Controller
              name="price"
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
          <Grid item xs={8}>
            <Typography variant="subtitle1">Upload Picture</Typography>
            <Controller
              name="imageurl"
              control={control}
              render={({ field }) => {
                return (
                  <TextField
                    {...field}
                    variant="outlined"
                    fullWidth
                    margin="dense"
                    placeholder="url"

                  />
                );
              }}
            />
            {/* <Button
              variant="contained"
              color="default"
              className={classes.button}
              startIcon={<CloudUploadIcon />}
              onClick={() => setOpenUpload(true)}
            >
              Upload
            </Button> */}
          </Grid>
        </Grid>
        <Controller
          name="attachment"
          control={control}
          render={({ field }) => (
            <DropzoneDialog
              acceptedFiles={["image/*"]}
              cancelButtonText={"cancel"}
              submitButtonText={"upload"}
              filesLimit={1}
              maxFileSize={5000000}
              open={openUpload}
              onClose={() => setOpenUpload(false)}
              onSave={async (files) => {
                const result = await readFileDataAsBase64(files[0]);
                field.onChange(result);
                setOpen(false);
              }}
              showPreviews={true}
              showFileNamesInPreview={true}
            />
          )}
        />
      </form>
    </Dialog>
  );
}
