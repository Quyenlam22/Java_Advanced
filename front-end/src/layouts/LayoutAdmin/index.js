import { Layout } from "antd";
import { Content } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import './LayoutAdmin.scss';
import logo from  "../../images/logo.png";
import { MenuFoldOutlined, MenuUnfoldOutlined, SearchOutlined, UserOutlined } from '@ant-design/icons'
import { useState } from "react";
import Notice from "../../components/Notice";
import MenuSider from "../../components/MenuSider";
import { Outlet } from "react-router-dom";

function LayoutAdmin () {
    const [collapse, setCollapse] = useState(false);

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
                            <div className="header-admin__nav-right__config">
                                <UserOutlined />
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