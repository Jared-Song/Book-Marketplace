import React from "react";
import { DataGrid } from "@mui/x-data-grid";
import { useSnackbar } from "notistack";
import { makeStyles } from "@material-ui/core/styles";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from "@material-ui/icons/Delete";
import axios from "axios";
import Grid from "@material-ui/core/Grid";
import EditBook from "./EditBook";

const useStyles = makeStyles((theme) => ({
  tableContainer: {
    height: 500,
  },
}));

export default function BooksTable({ books, refetch, token }) {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();

  const onDeleteBook = async (bookId) => {
    try {
      const { status } = await axios.delete(
        process.env.NEXT_PUBLIC_BOOK_URL + bookId,
        { headers: { Authentication: `Bearer ${token}` } }
      );
      if (status === 200) {
        enqueueSnackbar(`Book: ${bookId} has been deleted`, {
          variant: "success",
        });
        refetch();
      } else {
        throw "error";
      }
    } catch (error) {
      enqueueSnackbar("Something is wrong!!", {
        variant: "error",
      });
    }
  };

  const columns = [
    { field: "id", headerName: "ID", width: 90 },
    {
      field: "title",
      headerName: "Title",
      width: 150,
    },
    {
      field: "action",
      headerName: " ",
      disableClickEventBubbling: true,
      disableColumnMenu: true,
      disableSelectionOnClick: true,
      sortable: false,
      renderCell: (params) => {
        return (
          <>
            <IconButton
              size="small"
              onClick={() => {
                onDeleteBook(params.row.id);
              }}
            >
              <DeleteIcon />
            </IconButton>
            <EditBook token={token} book={params.row} refetch={refetch} />
          </>
        );
      },
    },
  ];

  const rows = React.useMemo(() => {
    return books.map((book) => {
      return {
        id: book.id,
        title: book.title,
      };
    });
  }, [books]);

  return (
    <div className={classes.tableContainer}>
      <DataGrid
        rows={rows}
        pageSize={10}
        columns={columns}
        disableSelectionOnClick
      />
    </div>
  );
}
