import Head from "next/head";
import React from "react";
import { SnackbarProvider } from "notistack";
import theme from "../src/theme";
import useAxios from "axios-hooks";

// MUI
import CssBaseline from "@material-ui/core/CssBaseline";
import { ThemeProvider } from "@material-ui/core/styles";

//Components
import Layout from "../src/components/layouts/Layout";
//Context
import { AuthProvider } from "../src/context/AuthContext";
import { ShoppingCartProvider } from "../src/context/ShoppingCartContext";

function MyApp({ Component, pageProps }) {
  React.useEffect(() => {
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
