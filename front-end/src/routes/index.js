import LayoutDefault from "../layouts/LayoutClient";
import Home from "../pages/client/Home";
import Category from "../pages/client/Category";
import Cart from "../pages/client/Cart";

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
                element: <Category/>
            },
            {
                path: "/cart",
                element: <Cart/>
            }
        ]
    }
] 