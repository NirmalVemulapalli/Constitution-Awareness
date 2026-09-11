import {
  Navigate,
  useLocation,
} from "react-router-dom";

import {
  useAuth,
} from "../context/AuthContext";

import Loading from "./Loading";


function ProtectedRoute({

  children,

  allowedRoles,

}) {

  const {

    user,
    loading,

  } = useAuth();


  const location =
    useLocation();


  if (loading) {

    return (
      <Loading />
    );

  }


  if (!user) {

    return (

      <Navigate
        to="/login"
        state={{
          from: location,
        }}
        replace
      />

    );
  }


  if (

    allowedRoles

    &&

    !allowedRoles.includes(
      user.role
    )

  ) {

    return (

      <Navigate
        to="/"
        replace
      />

    );
  }


  return children;

}


export default ProtectedRoute;