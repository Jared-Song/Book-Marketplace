import React from "react";
import LeftMenuBar from "../../src/components/admin/LeftMenuBar";
import withSession from "../../src/lib/session";
import useAxios from "axios-hooks";
import SimpleLoadingPlaceholder from "../../src/components/layouts/SimpleLoadingPlaceholder";
import { isEmpty } from "lodash";
import Grid from "@material-ui/core/Grid";
import { makeStyles } from "@material-ui/core/styles";
import CsvDownloadButton from "../../src/components/csv/CsvDownloadButton";
const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(2)
  }
}));

export default function CSVTest() {
  const classes = useStyles();
  const [{ data, loading, error }, refetch] = useAxios(
    process.env.NEXT_PUBLIC_BOOK_URL + "all"
  );

  if (loading && error) {
    return (<SimpleLoadingPlaceholder />);
  }
  console.log("swag");
  return (
    <Grid item xs={12}>
        <CsvDownloadButton table= {[
            ["yeah", "huh"],
            ["whack", "mhm"]
        ]} />
    </Grid>
  );
}
