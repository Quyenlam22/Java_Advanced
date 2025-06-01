import { message } from "antd";
import Cookies from "js-cookie";
import { Navigate, Outlet, useLocation } from "react-router-dom";

function PrivateRoute () {
    const token = Cookies.get("token");
    const location = useLocation();
    // const [messageApi, contextHolder] = message.useMessage();

    return (
        <>
            {token ? <Outlet/> : (
                <>
                {
                    // messageApi.open({
                    //     type: 'error',
                    //     content: 'This is an error message',
                    // })
                }
                    {/* {contextHolder} */}
                    <Navigate to={location.pathname}/>
                </>
            )}
        </>
    )
}

export default PrivateRoute;