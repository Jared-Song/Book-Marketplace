import { Select, MenuItem } from "@material-ui/core";
import React from "react";
import { useSnackbar } from "notistack";
import axios from "axios";
import { useCurrentUser } from "../../context/AuthContext";

export default function EditTransactionsStatus({ transaction, refetch,isAdmin, type }) {
  const [status, setStatus] = React.useState(transaction.status);
  const [isLoading, setIsLoading] = React.useState(false);
  const { token } = useCurrentUser();
  const { enqueueSnackbar } = useSnackbar();

  const updateStatus = async (pendingUpdateStatus) => {
    try {
      setStatus(pendingUpdateStatus);
      setIsLoading(true);
      await axios.post(
        process.env.NEXT_PUBLIC_TRANSACTION_URL +
          "updateStatus/" +
          transaction.id,
        {
          status: pendingUpdateStatus,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      enqueueSnackbar("Status Updated", {
        variant: "success",
      });
      setIsLoading(false);
    } catch (e) {
      console.log(e);
      enqueueSnackbar("Fail to update status", {
        variant: "error",
      });
    }
  };
  const isDsable = ()=>{
    return isLoading || (!isAdmin && type =="orders")
  }

  return (
    <Select
      disabled={isDsable()}
      size="small"
      value={status}
      onChange={(event) => {
        updateStatus(event.target.value);
      }}
    >
      <MenuItem value="DELIVERED">DELIVERED</MenuItem>
      <MenuItem value="IN_TRANSIT">IN_TRANSIT</MenuItem>
      <MenuItem value="REFUNDED">REFUNDED</MenuItem>
      <MenuItem value="CANCELLED">CANCELLED</MenuItem>
      <MenuItem value="PRE_ORDER">PRE_ORDER</MenuItem>
      <MenuItem value="PROCESSING">PROCESSING</MenuItem>
      <MenuItem value="REFUND_PENDING">REFUND_PENDING</MenuItem>
    </Select>
  );
}
