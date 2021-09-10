import styles from "../../../styles/Home.module.css";
import Typography from "@material-ui/core/Typography";
import { Button, Link } from "@material-ui/core";
import Router from "next/router";


export default function CsvDownloadButton({ table }) {
    return (
        <>
            <Button
                className={styles.teamName}
                onClick={() => {
                    var link = document.createElement("a");
                    link.setAttribute("href", encodeURI("data:text/csv;charset=utf-8," + table.map(e => e.join(",")).join("\n")));
                    link.setAttribute("download", "my_data.csv");
                    document.body.appendChild(link); // Required for FF

                    link.click();
                }}
            >
                CLICK HERE TO DOWNLOAD CSV
            </Button>
      </>
    );
  }
  