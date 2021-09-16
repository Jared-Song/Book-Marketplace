import React from "react";
import Footer from "../general/Footer";
import MenuBar from "../general/MenuBar";

export default function Layout({ children }) {
  return (
    <>
      <MenuBar />
      <div style={{ display: "flex", justifyContent: "center" }}>
      {/* <div> */}
        {children}
      </div>
      <Footer />
    </>
  );
}