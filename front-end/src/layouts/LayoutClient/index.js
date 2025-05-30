import { Link, NavLink, Outlet } from "react-router-dom"
import { Button, Dropdown, Layout, Menu } from "antd";
import { Content, Footer } from "antd/es/layout/layout";
import logo from "../../images/logo.png";
import "./LayoutDefault.scss";
import { BankOutlined, ClockCircleOutlined, CreditCardOutlined, EnvironmentOutlined, FacebookOutlined, HomeOutlined, IdcardOutlined, InstagramOutlined, MailOutlined, PhoneOutlined, PinterestOutlined, ShoppingCartOutlined, TwitterOutlined, UnorderedListOutlined, UserOutlined } from '@ant-design/icons';
import { Input } from 'antd';
import CartMini from "../../components/Cart/CartMini";
import Login from "../../components/Login";
const { Search } = Input;

const items = [
    {
        key: "/",
        icon: <HomeOutlined />,
        label: <NavLink to="/">Trang chủ</NavLink>
    },
    {
        key: "categories",
        icon: <UnorderedListOutlined />,
        label: <NavLink to="categories">Thể loại</NavLink>
    },
    {
        key: "cart",
        label: <NavLink to="cart"><CartMini/></NavLink>
    }
]

const login = [
    {
        key: "userinfo",
        // icon: <HomeOutlined />,
        label: <NavLink to="user-info">Thông tin tài khoản</NavLink>
    },
    {
        key: "logout",
        // icon: <UnorderedListOutlined />,
        label: <NavLink to="logout">Đăng xuất</NavLink>
    }
]

const unLogin = [
    {
        key: "login",
        // icon: <HomeOutlined />,
        label: <Login/>
    },
    {
        key: "register",
        // icon: <UnorderedListOutlined />,
        label: 'Đăng ký'
    }
]

function LayoutDefault () {
    // const onSearch: SearchProps['onSearch'] = (value, _e, info) => console.log(info?.source, value);
    const onSearch = (value, _e, info) =>
        console.log(info === null || info === void 0 ? void 0 : info.source, value
    );
    return (
        <>
            <Layout>
                <header className="header">
                    <div className="header__logo">
                        <img src={logo} alt="Logo"/>
                    </div>

                    <div className="header__search">
                        <Search
                            placeholder="Nhập từ khóa tìm kiếm"
                            enterButton="Tìm kiếm"
                            size="large"
                            allowClear
                            // suffix={<AudioOutlined/>}
                            onSearch={onSearch}
                        />
                    </div>
                    
                    <Menu className="header__menu"
                        mode="horizontal"
                        defaultSelectedKeys={['/']}
                        items={items}
                        // style={{ flex: 1, minWidth: 0 }}
                    />
                    <div className="auth">
                        <Dropdown menu={{ items: unLogin }} placement="bottom">
                            <Button><UserOutlined /></Button>
                        </Dropdown>
                    </div>
                </header>
                <Content className="content">
                    <div
                        style={{
                            minHeight: 400,
                        }}
                    >
                    <Outlet/>
                    </div>
                </Content>
                {/* <Footer className="footer">
                    Copyright ©{new Date().getFullYear()} Created by Group 8
                </Footer> */}
                <Footer className="footer">
                    <div className="footer__main">
                        <div className="footer__box">
                            <h3 className="footer__box__title">Book Store</h3>
                            <div className="footer__box__content">
                                <p>Điểm đến hàng đầu của bạn cho các sản phẩm chất lượng và trải nghiệm mua sắm tuyệt vời.</p>
                                <div className="footer__box__icons">
                                    <Link className="footer__box__icon"><FacebookOutlined /></Link>
                                    <Link className="footer__box__icon"><TwitterOutlined /></Link>
                                    <Link className="footer__box__icon"><InstagramOutlined /></Link>
                                    <Link className="footer__box__icon"><PinterestOutlined /></Link>
                            </div>
                            </div>    
                        </div>
                        <div className="footer__box">
                            <h3 className="footer__box__title">Shop</h3>
                            <ul className="footer__box__content">
                                <li><Link to="/" className="footer__box__desc">Sản phẩm mới</Link></li>
                                <li><Link to="/" className="footer__box__desc">Sản phẩm bán chạy nhất</Link></li>
                                <li><Link to="/" className="footer__box__desc">Xu hướng hiện nay</Link></li>
                                <li><Link to="/" className="footer__box__desc">Mặt hàng giảm giá</Link></li>
                                <li><Link to="/" className="footer__box__desc">Bộ sưu tập sách</Link></li>
                            </ul>
                        </div>
                        <div className="footer__box">
                            <h3 className="footer__box__title">Chăm sóc khách hàng</h3>
                            <ul className="footer__box__content">
                                <li><Link to="/" className="footer__box__desc">Liên hệ với chúng tôi</Link></li>
                                <li><Link to="/" className="footer__box__desc">Câu hỏi thường gặp</Link></li>
                                <li><Link to="/" className="footer__box__desc">Chính sách vận chuyển</Link></li>
                                <li><Link to="/" className="footer__box__desc">Đổi / Trả hàng</Link></li>
                                <li><Link to="/" className="footer__box__desc">Hướng dẫn</Link></li>
                            </ul>
                        </div>
                        <div className="footer__box">
                            <h3 className="footer__box__title">Contact Us</h3>
                            <ul className="footer__box__content">
                                <li className="flex items-start">
                                    <EnvironmentOutlined className="mr-1" />
                                    <span className="text-gray-400">123 Bac Tu Liem, Ha Noi, Viet Nam</span>
                                </li>
                                <li className="flex items-center">
                                    <PhoneOutlined className="mr-1" />
                                    <span className="text-gray-400">+1 (555) 123-4567</span>
                                </li>
                                <li className="flex items-center">
                                    <MailOutlined className="mr-1" />
                                    <span className="text-gray-400">support@bookstore.com</span>
                                </li>
                                <li className="flex items-center">
                                    <ClockCircleOutlined className="mr-1" />
                                    <span className="text-gray-400">Mon-Fri: 9AM-6PM</span>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <hr/>

                    <div className="footer__copyright">
                        <div className="footer__copyright__content">
                            Copyright ©{new Date().getFullYear()} Created by Group 8
                        </div>
                        <div className="footer__copyright__box">
                            <Link to="/" className="footer__copyright__terms">Chính sách bảo mật</Link>
                            <Link to="/" className="footer__copyright__terms">Điều khoản dịch vụ</Link>
                            <div className="footer__copyright__icons">
                                <Link className="footer__box__icon"><CreditCardOutlined /></Link>
                                <Link className="footer__box__icon"><BankOutlined /></Link>
                                <Link className="footer__box__icon"><IdcardOutlined /></Link>
                            </div>
                        </div>
                    </div>
                </Footer>
            </Layout>
        </>
    )
}

export default LayoutDefault