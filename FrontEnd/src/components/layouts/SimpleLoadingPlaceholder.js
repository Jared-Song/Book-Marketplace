import React from "react";
import Grid from "@material-ui/core/Grid";
import Skeleton from "@material-ui/lab/Skeleton";

export default function SimpleLoadingPlaceholder() {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Skeleton variant="rect" height={220} />
      </Grid>
      {/* <Grid item xs={12}>
        <Skeleton variant="rect" width={210} height={120} />
      </Grid>
      <Grid item xs={12}>
        <Skeleton variant="rect" width={210} height={120} />
      </Grid> */}
    </Grid>
  );
}
