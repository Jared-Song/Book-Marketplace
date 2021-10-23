import React from "react";

//MUI
import Avatar from "@material-ui/core/Avatar";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import ListItemText from "@material-ui/core/ListItemText";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Typography from "@material-ui/core/Typography";

//Icons
import Filter1Icon from "@material-ui/icons/Filter1";
import Filter2Icon from "@material-ui/icons/Filter2";
import Filter3Icon from "@material-ui/icons/Filter3";
import Filter4Icon from "@material-ui/icons/Filter4";
import Filter5Icon from "@material-ui/icons/Filter5";

const useStyles = makeStyles((theme) => ({
  root: {
    marginTop: 45
  },
  teamList: {
    width: "100%",
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
    margin: theme.spacing(4),
  },
}));

export default function Teaminfo() {
  const classes = useStyles();

  return (
    <div container className={classes.root} >
      <Typography variant="h5">Group Member Information</Typography>
      <List className={classes.teamList}>
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
