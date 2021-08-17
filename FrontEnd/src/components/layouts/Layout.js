import React from "react";
import Footer from "../general/Footer";
import MenuBar from "../general/MenuBar";

export default function Layout({children}) {
    return (
        <>
        <MenuBar />
        <div style={{marginTop: 65, display: "flex", justifyContent: "center"}}>
               {children}
        </div>
        <Footer />
        </>
    )
}