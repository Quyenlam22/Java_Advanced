import Cookies from "js-cookie";
import { Navigate, Outlet, useLocation } from "react-router-dom";

function PrivateRoute () {
    const token = Cookies.get("token");
    const location = useLocation();

    return (
        <>
            {token ? <Outlet/> : (
                <>
                    <Navigate to={location.pathname}/>
                </>
            )}
        </>
    )
}

export default PrivateRoute;