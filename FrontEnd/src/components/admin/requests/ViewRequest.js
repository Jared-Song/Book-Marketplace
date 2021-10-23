import axios from "axios";
import isEmpty from "lodash/isEmpty";
import React from "react";
import { useSnackbar } from "notistack";

//Components
import RequestFormDialog from "./RquestFormDialog";

//ICON
import EditIcon from "@material-ui/icons/Edit";
import IconButton from "@material-ui/core/IconButton";

export default function  ViewBook({ token, refetch, book }) {
  const { enqueueSnackbar } = useSnackbar();
  const [open, setOpen] = React.useState(false);

  const onEditBook = async (data) => {
    try {
      const { status } = await axios.post(
        process.env.NEXT_PUBLIC_EDIT_BOOK_URL + book.id,
        data,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      enqueueSnackbar("Book edited!", {
        variant: "success",
      });
      refetch();
      setOpen(false);
    } catch (error) {
      enqueueSnackbar("Something is wrong when edit!!", {
        variant: "error",
      });
    }
  };

  if (isEmpty(book)) {
    return null;
  }

  return (
    <>
      <IconButton size="small">
        <EditIcon
          onClick={() => {
            setOpen(true);
          }}
        />
      </IconButton>
      <RequestFormDialog
        open={open}
        existingBook={book}
        setOpen={setOpen}
        onSubmit={onEditBook}
        title="Edit Book"
      />
    </>
  );
}
