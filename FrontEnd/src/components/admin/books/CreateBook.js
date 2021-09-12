import React from "react";
import Fab from "@material-ui/core/Fab";
import AddIcon from "@material-ui/icons/Add";
import { useSnackbar } from "notistack";
import axios from "axios";
import BookFormDialog from "./BookFormDialog";


export default function CreateBook({ token, refetch }) {
  const { enqueueSnackbar } = useSnackbar();
  const [open, setOpen] = React.useState(false)

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
        enqueueSnackbar("Book created!", {
          variant: "success",
        });
        refetch();
        setOpen(false);
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
      <BookFormDialog title="Create Book" open={open} setOpen={setOpen}  onSubmit={onCreateBook}/>
    </>
  );
}
