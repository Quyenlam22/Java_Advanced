import { Button, Dropdown, Layout } from "antd";
import { Content } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import './LayoutAdmin.scss';
import logo from  "../../images/logo.png";
import { MenuFoldOutlined, MenuUnfoldOutlined, SearchOutlined, UserOutlined } from '@ant-design/icons'
import { useState } from "react";
import Notice from "../../components/Notice";
import MenuSider from "../../components/MenuSider";
import { NavLink, Outlet } from "react-router-dom";
import Cookies from 'js-cookie';
import Logout from "../../components/Logout";
import Login from "../../components/Login";

function LayoutAdmin () {
    const [collapse, setCollapse] = useState(false);

    const token = Cookies.get('token');
    const fullName = Cookies.get('full_name') || "";
    const [username, setUsername] = useState(fullName);

    const login = [
        {
            key: "userinfo",
            label: <NavLink to="user-info">Thông tin tài khoản</NavLink>
        },
        {
            key: "logout",
            label: <Logout setUsername={setUsername}/>
        }
    ]

    const unLogin = [
        {
            key: "login",
            label: <Login setUsername={setUsername}/>
        },
        {
            key: "register",
            label: 'Đăng ký'
        }
    ]

    return (
        <>
            <Layout className="layout-default">
                <header className="header-admin">
                    <div className={"header-admin__logo " + (collapse && "header-admin__logo--collapse")}>
                        <div className="header-admin__logo__image">
                            <img src={logo} alt="Logo"/>
                        </div>
                    </div>
                    <div className="header-admin__nav">
                        <div className="header-admin__nav-left">
                            <div className="header-admin__collapse" onClick={() => setCollapse(!collapse)}>
                                {collapse ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                            </div>
                            <div className="header-admin__search">
                                <SearchOutlined />
                            </div>
                        </div>
                        <div className="header-admin__nav-right">
                            <div className="header-admin__nav-right__notify">
                                <Notice />
                            </div>
                            {/* <div className="header-admin__nav-right__avatar">
                                <img src={avatar} alt="Avatar"/>
                            </div> */}
                            <div className="header-admin__nav-right__auth">
                                <Dropdown menu={{ items: token ? login : unLogin }} placement="bottom">
                                    <Button>{username ? username : <UserOutlined />}</Button>
                                </Dropdown>
                            </div>
                        </div>
                    </div>
                </header>
                <Layout>
                    <Sider theme={"light"} className="sider" collapsed={collapse}>
                        <MenuSider/>
                    </Sider>
                    <Content className="content-admin">
                        <Outlet/>
                    </Content>
                </Layout>
                {/* <Footer>Footer</Footer> */}
            </Layout>
        </>
    )
}

export default LayoutAdmin;