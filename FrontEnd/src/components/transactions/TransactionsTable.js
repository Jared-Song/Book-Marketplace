import React from "react";
import {
  DataGrid,
  GridToolbarContainer,
  GridToolbarExport,
} from "@mui/x-data-grid";
import { useSnackbar } from "notistack";
import { makeStyles } from "@material-ui/core/styles";
import axios from "axios";
import { Button } from "@material-ui/core";
import ReviewDialog from "./ReviewDialog";
import _ from "lodash";
import EditTransactionsStatus from "./EditTransactionStatus";

const useStyles = makeStyles((theme) => ({
  tableContainer: {
    height: 700,
  },
}));
function CustomToolbar() {
  return (
    <GridToolbarContainer>
      <GridToolbarExport />
    </GridToolbarContainer>
  );
}

export default function TransactionsTable({
  transactions,
  refetch,
  token,
  isAdmin,
  type,
}) {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();
  console.log(transactions)
  const refund = async (id) => {
    try {
      await axios.get(
        process.env.NEXT_PUBLIC_TRANSACTION_URL + id + "/refund",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      enqueueSnackbar("Order pending refund!", {
        variant: "success",
      });
      refetch();
    } catch (e) {
      console.log(e);
      enqueueSnackbar("Something is wrong!!", {
        variant: "error",
      });
    }
  };

  const columns = [
    { field: "id", headerName: "ID", width: 100 },
    {
      field: "status",
      headerName: "Status",
      width: 150,
      renderCell: (params) => {
        return <EditTransactionsStatus transaction={params.row} refetch={refetch} />;
      }
    },
    {
      field: "createdAt",
      headerName: "Date",
      width: 130,
      valueGetter: (params) =>
        new Date(params.row.createdAt).toISOString().split("T")[0],
    },
    {
      field: "price",
      headerName: "Price",
      width: 130,
    },
    {
      field: "price",
      headerName: "Price",
      width: 130,
      valueFormatter: ({ value }) => currencyFormatter.format(Number(value)),
    },
    {
      field: "buyer",
      headerName: "Buyer",
      width: 130,
    },
    {
      field: "seller",
      headerName: "Seller",
      width: 130,
    },
    {
      field: "book",
      headerName: "Book Name",
      width: 150,
    },
    {
      field: "Review",
      headerName: " ",
      hide: isAdmin, 
      disableClickEventBubbling: true,
      disableColumnMenu: true,
      disableSelectionOnClick: true,
      sortable: false,
      width: 130,
      renderCell: (params) => {
        const row = _.find(transactions, (item) => {
          return item.id === params.row.id;
        });
        if (params.row.status === "DELIVERED" && !row.isReviewed) {
          return <ReviewDialog order={row} refetch={refetch} />;
        }
      },
    },
    {
      field: "Refund",
      headerName: " ",
      hide: isAdmin, 
      disableClickEventBubbling: true,
      disableColumnMenu: true,
      disableSelectionOnClick: true,
      sortable: false,
      width: 130,
      renderCell: (params) => {
        if (params.row.status === "PROCESSING" && type === "orders") {
          return (
            <Button
              variant="outlined"
              onClick={() => {
                refund(params.row.id);
              }}
            >
              Refund
            </Button>
          );
        }
      },
    },
  ];

  const rows = React.useMemo(() => {
    return transactions.map((tran) => {
      console.log(tran);
      return {
        id: tran.id,
        status: tran.status,
        createdAt: tran.createdAt,
        price: tran.price,
        buyer: tran.buyer.username,
        seller: tran.book.seller.username,
        book: tran.book.title,
      };
    });
  }, [transactions]);

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
