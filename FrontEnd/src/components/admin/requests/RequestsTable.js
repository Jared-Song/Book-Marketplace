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
    // try {
    //   const { status } = await axios.delete(
    //     process.env.NEXT_PUBLIC_BOOK_URL + bookId,
    //     { headers: { Authentication: `Bearer ${token}` } }
    //   );
    //   if (status === 200) {
    //     enqueueSnackbar(`Book: ${bookId} has been deleted`, {
    //       variant: "success",
    //     });
    //     refetch();
    //   } else {
    //     throw "error";
    //   }
    // } catch (error) {
    //   enqueueSnackbar("Something is wrong!!", {
    //     variant: "error",
    //   });
    // }
  };

  // const getUser = (userId)=>{
  //   axios
  //     .get(process.env.NEXT_PUBLIC_USERS_URL + userId)
  //     .then((res) => {
  //       res
  //     })
  //     .catch((error) => {
  //       enqueueSnackbar("Something is wrong!!", {
  //         variant: "error",
  //       });
  //     });
  //  Axios(
      
  //   );
  
    
  //   return data
  // }
  const getRequestFrom = (params) =>{
    switch (params.row.requestType) {
      case "NEW_BUSINESS_USER":
        return getUser(params.row.objectId).username
      case "NEW_BOOK_LISTING":
        return "In Transit";
      case "TO_BUSINESS_USER":
        return "Delivered";
    }

  }
  const getRequestObject = (params) =>{

    
  }

  const columns = [
    { field: "id", headerName: "ID", width: 90 },
    {
      field: "requestType",
      headerName: "Request Type",
      width: 180,
    },
    {
      field: "requestFrom",
      headerName: "Request From",
      width: 180,
      valueGetter: getRequestFrom
    },
    {
      field: "requestObejct",
      headerName: "Request Obejct",
      width: 180,
      valueGetter: getRequestObject

    },
    
    { field: "objectId", hide: true },
  
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
                onApprove(params.row.id);
              }}
            >
              <DeleteIcon />
            </IconButton>
            <ViewBook token={token} book={params.row} refetch={refetch} />
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
