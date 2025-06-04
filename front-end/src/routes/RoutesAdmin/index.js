import DashBoard from "../../pages/admin/DashBoard";
import LayoutAdmin from "../../layouts/LayoutAdmin/index";
import CategoryAdmin from "../../pages/admin/CategoryAdmin";
import BookAdmin from "../../pages/admin/BookAdmin";
import AuthorAdmin from "../../pages/admin/AuthorAdmin";
import Setting from "../../pages/admin/Setting";
import UserAdmin from "../../pages/admin/UserAdmin";
import UserClient from "../../pages/admin/UserClient";

export const routesAdmin = [
    {
        path: "/admin",
        element: <LayoutAdmin/>,
        children: [
            {
                path: "dashboard",
                element: <DashBoard/>,
            },
            {
                index: true,
                element: <DashBoard/>,
            },
            {
                path: "categories",
                element: <CategoryAdmin/>,
            },
            {
                path: "books",
                element: <BookAdmin/>,
            },
            {
                path: "authors",
                element: <AuthorAdmin/>,
            },
            {
                path: "userClient",
                element: <UserClient/>,
            },
            {
                path: "userAdmin",
                element: <UserAdmin/>,
            },
            {
                path: "settings",
                element: <Setting/>,
            },
        ]
    }
]