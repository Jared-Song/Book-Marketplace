import React from "react";
import { useJwt } from "react-jwt";

const AuthContext = React.createContext(null);

export const AuthProvider = ({ token, children, loading }) => {
  const [currentToken, setToken] = React.useState(token);
  const { decodedToken } = useJwt(currentToken);
  const [isLoading, setIsLoading] = React.useState(loading);
  React.useEffect(() => {
    setToken(token);
  }, [token]);
  return (
    <AuthContext.Provider
      value={{ currentUser: decodedToken, setToken, isLoading }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useCurrentUser = () => React.useContext(AuthContext);
