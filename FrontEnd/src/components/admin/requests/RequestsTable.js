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
import ViewBook from "./ViewRequest";
import { object } from "yup/lib/locale";
import { Button } from "@material-ui/core";

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

  // const getUser = async (userId) => {
  //   await axios
  //     .get(process.env.NEXT_PUBLIC_USERS_URL + userId)
  //     .then((res) => {
  //       return res.data;
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //     });
  // };

  // const getBook = async (bookId) => {
  //   await axios
  //     .get(process.env.NEXT_PUBLIC_BOOK_URL + bookId)
  //     .then((res) => {
  //       console.log(res.data);
  //       return res.data;
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //     });
  // };
  // const getRequestFrom = async (params) => {
  //   switch (params.row.requestType) {
  //     case "NEW_BUSINESS_USER":
  //       return getUser(params.row.objectId).username;
  //     case "NEW_BOOK_LISTING":
  //       return await getBook(params.row.objectId).title;
  //     case "TO_BUSINESS_USER":
  //       return "Delivered";
  //   }
  // };

  const columns = [
    { field: "id", headerName: "ID", width: 90 },
    {
      field: "requestType",
      headerName: "Request Type",
      width: 180,
    },
    {
      field: "objectId",
      headerName: "Request Obejct",
      width: 180,
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
            <Button
              size="small"
              onClick={() => {
                onApprove(params.row.id);
              }}
            >
              Approve
            </Button>
            {/* <ViewBook token={token} book={params.row} refetch={refetch} /> */}
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
        objectId: request.objectId,
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
