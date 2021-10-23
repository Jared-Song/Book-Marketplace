import axios from "axios";
import React from "react";
import { useSnackbar } from "notistack";

//MUI
import {
  DataGrid,
  GridToolbarContainer,
  GridToolbarExport,
} from "@mui/x-data-grid";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Button from "@material-ui/core/Button";

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

export default function RequestsTable({ requests, refetch, token }) {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();

  const onApprove = async (id) => {
    try {
      const { status } = await axios.post(
        process.env.NEXT_PUBLIC_REQUEST_URL + "approve/" + id,
        { headers: { Authentication: `Bearer ${token}` } }
      );
      if (status === 200) {
        enqueueSnackbar(`Request: ${id} has been approved`, {
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
  const onReject = async (id) => {
    try {
      const { status } = await axios.delete(
        process.env.NEXT_PUBLIC_REQUEST_URL + id,
        { headers: { Authentication: `Bearer ${token}` } }
      );
      if (status === 200) {
        enqueueSnackbar(`Request: ${id} has been rejected`, {
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
      field: "requestType",
      headerName: "Request Type",
      width: 180,
    },
    {
      field: "requestBy",
      headerName: "From",
      width: 150,
    },
    {
      field: "created_At",
      headerName: "Date",
      width: 150,
      valueGetter: (params) =>
        new Date(params.row.created_At).toISOString().split("T")[0],
    },
    {
      field: "request",
      headerName: "Request",
      width: 200,
    },
    {
      field: "action",
      headerName: "Actions",
      disableClickEventBubbling: true,
      disableColumnMenu: true,
      disableSelectionOnClick: true,
      sortable: false,
      width: 200,

      renderCell: (params) => {
        return (
          <>
            <Button
              color="primary"
              size="small"
              onClick={() => {
                onApprove(params.row.id);
              }}
            >
              Approve
            </Button>
            |
            <Button
              color="secondary"
              size="small"
              onClick={() => {
                onReject(params.row.id);
              }}
            >
              Reject
            </Button>
          </>
        );
      },
    },
  ];

  const rows = React.useMemo(() => {
    return requests.map((request) => {
      return {
        id: request.id,
        requestType: request.requestType,
        requestBy: request.user.username,
        request: request.request,
        created_At: request.created_At
      };
    });
  }, [requests]);

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
