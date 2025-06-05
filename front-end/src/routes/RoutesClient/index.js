import LayoutDefault from "../../layouts/LayoutClient";
import Home from "../../pages/client/Home";
import Category from "../../pages/client/Category";
import Cart from "../../pages/client/Cart";
import AllCategory from "../../components/TopCategory/AllCategory";
import PrivateRoute from "../../components/PrivateRoute";
import Order from "../../pages/client/Order";
import SearchResult from "../../pages/client/SearchResult";

export const routesClient = [
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
                path: "/search",
                element: <SearchResult/>
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