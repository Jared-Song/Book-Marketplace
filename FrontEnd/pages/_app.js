// import { Provider } from "next-auth/client";
import React, { useState } from "react";
import Head from "next/head";
import { ThemeProvider } from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";
import useAxios from "axios-hooks";
import theme from "../src/theme";
import Layout from "../src/components/layouts/Layout";
import { SnackbarProvider } from "notistack";
import { AuthProvider } from "../src/context/AuthContext";
import useSWR from "swr";
import { ShoppingCartProvider } from "../src/context/ShoppingCartContext";
// import { signIn, useSession } from "next-auth/client";

function MyApp({ Component, pageProps }) {
  // const [session, loading] = useSession();
  React.useEffect(() => {
    // Remove the server-side injected CSS.
    const jssStyles = document.querySelector("#jss-server-side");
    if (jssStyles) {
      jssStyles.parentElement.removeChild(jssStyles);
    }
  }, []);
  const [{ data, loading }] = useAxios("/api/user");
  return (
    <>
      <Head>
        <title>BOOKEROO</title>
        <meta
          name="viewport"
          content="minimum-scale=1, initial-scale=1, width=device-width"
        />
      </Head>
      <ThemeProvider theme={theme}>
        <SnackbarProvider maxSnack={3}>
          <AuthProvider token={data && data.token} loading={loading}>
            <ShoppingCartProvider>
              <Layout>
                <CssBaseline />
                <Component {...pageProps} />
              </Layout>
            </ShoppingCartProvider>
          </AuthProvider>
        </SnackbarProvider>
      </ThemeProvider>
    </>
  );
}

export default MyApp;
