import React from "react";
import {
  DataGrid,
  GridToolbarContainer,
  GridToolbarExport,
} from "@mui/x-data-grid";
import { useSnackbar } from "notistack";
import { makeStyles } from "@material-ui/core/styles";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from "@material-ui/icons/Delete";
import axios from "axios";
import EditBook from "./EditBook";

const useStyles = makeStyles((theme) => ({
  tableContainer: {
    height: 500,
  },
}));
function CustomToolbar() {
  return (
    <GridToolbarContainer>
      <GridToolbarExport csvOptions={{ allColumns: true }} />
    </GridToolbarContainer>
  );
}

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
      width: 120,
    },
    {
      field: "bookStatus",
      headerName: "Stutus",
      width: 120,
    },
    {
      field: "authorName",
      headerName: "Author",
      width: 120,
    },
    {
      field: "isbn",
      headerName: "isbn",
      width: 120,
    },
    {
      field: "quantity",
      headerName: "Qt.",
      width: 100,
    },
    {
      field: "price",
      headerName: "$",
      width: 100,
      valueFormatter: ({ value }) => currencyFormatter.format(Number(value)),
    },
    { field: "sellerId", hide: true },
    { field: "category", hide: true },
    { field: "imageURL", hide: true },
    {
      field: "action",
      headerName: " ",
      disableClickEventBubbling: true,
      disableColumnMenu: true,
      disableSelectionOnClick: true,
      sortable: false,
      renderCell: (params) => {
        const currentBook = _.find(books, (item) => {
          return item.id === params.row.id;
        });
        return (
          <>
            <EditBook token={token} book={currentBook} refetch={refetch} />
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
        bookStatus: book.bookStatus,
        authorName: book.authorName,
        isbn: book.isbn,
        quantity: book.quantity,
        price: book.price,
        category: book.category,
        quality: book.quality,
        sellerId: book.sellerId,
        imageURL: book.imageURL,
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
        checkboxSelection
        components={{
          Toolbar: CustomToolbar,
        }}
      />
    </div>
  );
}

const currencyFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
});
