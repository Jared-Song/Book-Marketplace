import styles from "../../../styles/Home.module.css";
import Typography from "@material-ui/core/Typography";
import { Button, Link } from "@material-ui/core";
import Router from "next/router";

export default function Footer() {
  return (
    <>
      <footer className={styles.footer}>
        <br />
        <Typography>SEPT Team Project.</Typography>
        <Typography>
          Present by{" "}
          <Link
            className={styles.teamName}
            onClick={() => {
              Router.push("/teaminfo");
            }}
          >
            Moving House
          </Link>
          .
        </Typography>
      </footer>
    </>
  );
}
