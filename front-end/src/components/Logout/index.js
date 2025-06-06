import Cookies from 'js-cookie';
import { notification } from 'antd';

function Logout (props) {
    const { setUsername } = props;

    const [api, contextHolder] = notification.useNotification();

    const openNotification = () => {
        api['success']({
            message: `Đăng xuất thành công!`,
            duration: 2
        });
    };

    const handleClick = () => {
        Cookies.remove('token');
        // Cookies.remove('cart');
        Cookies.remove('full_name');
        setUsername("");
        openNotification();
    }

    return (
        <>
            {contextHolder}
            <div onClick={handleClick}>Đăng xuất</div>
        </>
    )
}

export default Logout;