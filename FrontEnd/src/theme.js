import { createTheme } from "@material-ui/core/styles";
import { red } from "@material-ui/core/colors";

// Create a theme instance.
const theme = createTheme({
  palette: {
    primary: {
      main: "#90caf9",
      light: '#a6d4fa',
      dark: '#648dae',
      contrastText: '#000',
    },
    secondary: {
      main: "#f8bbd0",
      light: '#f9c8d9',
      dark: '#ad8291',
      contrastText: '#000',
    },
    error: {
      main: red.A400,
    },
    background: {
      default: "#fafafa",
    },
  },
  typography: {
    allVariants: {
      color: "#474D66",
    },
  },
});


export default theme;
