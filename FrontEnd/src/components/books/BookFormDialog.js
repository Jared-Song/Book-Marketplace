import React from "react";
import useAxios from "axios-hooks";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import CloseIcon from "@material-ui/icons/Close";
import Slide from "@material-ui/core/Slide";
import { Controller, useForm } from "react-hook-form";
import { Grid, TextField } from "@material-ui/core";
import CloudUploadIcon from "@material-ui/icons/CloudUpload";
import { DropzoneDialog } from "material-ui-dropzone";
import readFileDataAsBase64 from "../../util/ReadFileDataAsBase64";
import { useCurrentUser } from "../../context/AuthContext";
import Autocomplete from "@material-ui/lab/Autocomplete";
import { find } from "lodash";
import NumberFormatCustom from "../../util/CustomNumberFormat";

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
    height: 40,
    position: "relative",
    top: theme.spacing(1),
  },
  autocomplete: { height: 40 },
  autocompleteInput: { position: "relative", bottom: theme.spacing(1) },
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
    defaultValues: existingBook || {
      quality: "NEW",
    },
  });
  const { token, currentUser } = useCurrentUser();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_USERS_URL + "all"
  );
  const [openUpload, setOpenUpload] = React.useState(false);
  if (loading || error) {
    return <></>;
  }

  const getDefaultSellers = (selectedId) => {
    console.log(existingBook)
    if (existingBook?.seller?.id) {
      return { title: existingBook.seller.fullName, id: existingBook.seller.id };
    }
    return undefined;
  };

  const getSellers = () => {
    return data.map(({ fullName, id }) => {
      return { title: fullName, id: id };
    });
  };
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
                    placeholder="Book Title"
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
          {existingBook && currentUser && currentUser.role === "ADMIN" && (
            <Grid item xs={6}>
              <Typography variant="subtitle1">Status</Typography>
              <Controller
                name="bookStatus"
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
                      <MenuItem value="AVAILABLE">Available</MenuItem>
                      <MenuItem value="UNAVAILABLE">Unavailable</MenuItem>
                      <MenuItem value="PENDING_APPROVAL">
                        Pending approval
                      </MenuItem>
                    </Select>
                  );
                }}
              />
            </Grid>
          )}

          { currentUser && currentUser.role !== "USER_NORMAL" && (
            <Grid item xs={6}>
              <Typography variant="subtitle1">Quality</Typography>
              <Controller
                name="quality"
                control={control}
                render={({ field }) => {
                  return (
                    <Select
                      name="quality"
                      fullWidth
                      size="small"
                      onChange={(event) => {
                        field.onChange(event.target.value);
                      }}
                      value={field.value}
                      className={classes.select}
                      variant="outlined"
                    >
                      <MenuItem value="NEW">New</MenuItem>
                      <MenuItem value="USED">Used</MenuItem>
                    </Select>
                  );
                }}
              />
            </Grid>
          )}

          {currentUser && currentUser.role === "ADMIN" && (
            <Grid item xs={6}>
              <Typography variant="subtitle1">Seller</Typography>
              <Controller
                name="sellerId"
                control={control}
                render={({ field }) => {
                  return (
                    <Autocomplete
                      disabled={existingBook}
                      className={classes.select}
                      options={getSellers()}
                      defaultValue={getDefaultSellers(field.value)}
                      onChange={(event, newValue) => {
                        field.onChange(newValue.id);
                      }}
                      classes={{
                        inputRoot: classes.autocomplete,
                        input: classes.autocompleteInput,
                      }}
                      getOptionLabel={(option) => option.title}
                      renderInput={(params) => {
                        return (
                          <TextField
                            {...params}
                            variant="outlined"
                            placeholder="Seller"
                            variant="outlined"
                          />
                        );
                      }}
                    />
                  );
                }}
              />
            </Grid>
          )}

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
                    InputProps={{
                      inputComponent: NumberFormatCustom,
                    }}
                  />
                );
              }}
            />
          </Grid>
          <Grid item xs={8}>
            <Typography variant="subtitle1">Upload Picture</Typography>
            <Controller
              name="imageURL"
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
          </Grid>
        </Grid>
      </form>
    </Dialog>
  );
}
