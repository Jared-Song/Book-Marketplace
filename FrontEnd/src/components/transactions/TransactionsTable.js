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
import EditBook from "./EditTransaction";
import { Button } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  tableContainer: {
    height: 500,
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
}) {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();

  const onDeleteTransaction = async (transactionId) => {
    try {
      const { status } = await axios.delete(
        process.env.NEXT_PUBLIC_TRANSACTION_URL + transactionId,
        { headers: { Authentication: `Bearer ${token}` } }
      );
      if (status === 200) {
        enqueueSnackbar(`Transaction: ${transactionId} has been deleted`, {
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
 const getStatus = (params) =>{

  switch (params.row.status) {
    case 0:
     return  "Processing";
    case 1:
      return "In Transit";
    case 2:
      return "Delivered";
  }
  
 }
  const columns = [
    { field: "id", headerName: "Transaction ID", width: 170 },
    {
      field: "status",
      headerName: "Status",
      width: 150,
      valueGetter: getStatus
    },
    {
      field: "created_At",
      headerName: "Date",
      width: 130,
      // valueGetter: (params) =>
      //     new Date(params.row.created_At).toISOString().split("T")[0],
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
      field: "view",
      headerName: " ",
      disableClickEventBubbling: true,
      disableColumnMenu: true,
      disableSelectionOnClick: true,
      sortable: false,
      width: 130,
      renderCell: (params) => {
        return <Button>View Details</Button>;
      },
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
            {isAdmin && (
              <>
                <IconButton
                  size="small"
                  onClick={() => {
                    onDeleteTransaction(params.row.id);
                  }}
                >
                  <DeleteIcon />
                </IconButton>
                <EditBook token={token} book={params.row} refetch={refetch} />
              </>
            )}
          </>
        );
      },
    },
  ];

  const rows = React.useMemo(() => {
    return transactions.map((tran) => {
      return {
        id: tran.id,
        status: tran.status,
        date: tran.create_At,
        price: tran.price,
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
