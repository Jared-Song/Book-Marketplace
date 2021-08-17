// import { Provider } from "next-auth/client";
import React from "react";
import Head from "next/head";
import { ThemeProvider } from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";
import theme from "../src/theme";
import Layout from "../src/components/layouts/Layout";
import { SnackbarProvider } from "notistack";
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


  return (
    <>
      <Head>
        <title>BOOKROO</title>
        <meta
          name="viewport"
          content="minimum-scale=1, initial-scale=1, width=device-width"
        />
      </Head>
      <ThemeProvider theme={theme}>
        {/* <Provider session={pageProps.session}> */}
          <Layout>
            <SnackbarProvider maxSnack={3}>
              <CssBaseline />
              <Component {...pageProps} />
            </SnackbarProvider>
          </Layout>
        {/* </Provider> */}
      </ThemeProvider>
    </>
  );
}

export default MyApp;
