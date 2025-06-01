import LayoutDefault from "../layouts/LayoutClient";
import Home from "../pages/client/Home";
import Category from "../pages/client/Category";
import Cart from "../pages/client/Cart";
import AllCategory from "../components/TopCategory/AllCategory";
import PrivateRoute from "../components/PrivateRoute";
import Order from "../pages/client/Order";

export const routes = [
    {
        path: "/",
        element: <LayoutDefault/>,
        children: [
            {
                index: true,
                element: <Home/>
            },
            {
                path: "/categories",
                element: <AllCategory/>,
                children: [
                    {
                        path: ":id",
                        element: <Category/>
                    }
                ]
            },
            {
                path: "/cart",
                element: <Cart/>
            }, 
            {
                element: <PrivateRoute/>,
                children: [
                    {
                        path: "/orders",
                        element: <Order/>
                    }
                ]
            }
        ]
    }
] 