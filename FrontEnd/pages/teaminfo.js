import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ImageIcon from "@material-ui/icons/Image";
import WorkIcon from "@material-ui/icons/Work";
import BeachAccessIcon from "@material-ui/icons/BeachAccess";
import Filter1Icon from "@material-ui/icons/Filter1";
import Filter2Icon from "@material-ui/icons/Filter2";
import Filter3Icon from "@material-ui/icons/Filter3";
import Filter4Icon from "@material-ui/icons/Filter4";
import Filter5Icon from "@material-ui/icons/Filter5";
import { Typography } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
    margin: theme.spacing(6),
  },
}));

export default function Teaminfo() {
  const classes = useStyles();

  return (
    <div container>
      <Typography variant="h5">Team Information</Typography>
      <List className={classes.root}>
        <ListItem>
          <ListItemAvatar>
            <Avatar>
              <Filter1Icon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText
            primary="Jared Song"
            secondary="s3857657@student.rmit.edu.au"
          />
        </ListItem>
        <ListItem>
          <ListItemAvatar>
            <Avatar>
              <Filter2Icon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText
            primary="Aili Gong"
            secondary="s3443647@student.rmit.edu.au"
          />
        </ListItem>
        <ListItem>
          <ListItemAvatar>
            <Avatar>
              <Filter3Icon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText
            primary="Alexander Aloi"
            secondary="s3842524@student.rmit.edu.au"
          />
        </ListItem>
        <ListItem>
          <ListItemAvatar>
            <Avatar>
              <Filter4Icon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText
            primary="Carl Karama"
            secondary="s3713721@student.rmit.edu.au"
          />
        </ListItem>
        <ListItem>
          <ListItemAvatar>
            <Avatar>
              <Filter5Icon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText
            primary="Shannon Dann"
            secondary="s3858053@student.rmit.edu.au"
          />
        </ListItem>
      </List>
    </div>
  );
}
