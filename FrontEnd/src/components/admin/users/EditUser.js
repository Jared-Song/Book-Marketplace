import React from "react";
import IconButton from "@material-ui/core/IconButton";
import EditIcon from "@material-ui/icons/Edit";
import { useSnackbar } from "notistack";
import axios from "axios";
import isEmpty from "lodash/isEmpty";
import UserFormDialog from "./UserFormDialog";

export default function EditBook({ token, refetch, user }) {
  const { enqueueSnackbar } = useSnackbar();
  const [open, setOpen] = React.useState(false);

  const onEditUser= async (data) => {
    try {
      const { status } = await axios.post(
        process.env.NEXT_PUBLIC_EDIT_USER_URL + user.id,
        {
          ...data,
          business: {
            companyName: data.companyName,
            abn: data.abn
          }
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (data.userStatus !== user.userStatus) {
        const result = await axios.post(
          process.env.NEXT_PUBLIC_EDIT_USER_URL + "userStatus/" + user.id,
          {
            ...data,
            business: {
              companyName: data.companyName,
              abn: data.abn,
            },
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
      }

      enqueueSnackbar(`User information for ID ${user.id} has been updated!`, {
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

  if (isEmpty(user)) {
    return null;
  }

  return (
    <>
      <IconButton size="small" disabled={user.role =="ADMIN"}>
        <EditIcon
          onClick={() => {
            setOpen(true);
          }}
        />
      </IconButton>
      <UserFormDialog
        open={open}
        user={user}
        setOpen={setOpen}
        onSubmit={onEditUser}
        title="Edit User"
      />
    </>
  );
}
