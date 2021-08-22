import React from "react";

const AuthContext = React.createContext(null);

export const AuthProvider = ({ user, children }) => {
  const [currentUser, setCurrentUser] = React.useState(user);
  React.useEffect(() => {
    setCurrentUser(user);
  }, [user]);
  return (
    <AuthContext.Provider value={{ currentUser, setCurrentUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useCurrentUser = () => React.useContext(AuthContext);
