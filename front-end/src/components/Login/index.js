import { Button, Checkbox, Form, Input, Modal, notification } from "antd";
import { useState } from "react";
import { checkLogin } from "../../services/authService";
// import { loginPost } from "../../services/authService";
import Cookies from 'js-cookie';
// import { getCart, getCartByUser } from "../../services/cartService";
// import { checkUser } from "../../utils/checkUser";

const rules = [{ 
    required: true, 
    message: 'Vui lòng không để trống!' 
}];

function Login (props) {
    const {setUsername} = props;
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [form] = Form.useForm();
    const [api, contextHolder] = notification.useNotification();

    const openNotification = (name) => {
        if(name) {
            api['success']({
                message: `Đăng nhập thành công!`,
                description: `Xin chào ${name}`,
                duration: 2
            });
        }
        else {
            api['error']({
                message: `Đăng nhập thất bại!`,
                description: `Sai tài khoản hoặc mật khẩu`,
                duration: 2
            });
        }
    };
    
    const showModal = () => {
        setIsModalOpen(true);
    };
    const onCancel = () => {
        setIsModalOpen(false);
        form.resetFields();
    }

    const onFinish = async (values) => {
        const result = await checkLogin(values.username, values.password);

        if(result.length > 0){
            setUsername(result[0].fullName);
            setIsModalOpen(false);

            // const cart = await getCartByUser(result[0].id);
            // console.log(cart)
            // if(cart) {
            //     Cookies.set("cart", cart.id);
            // }
            // else {
            //     checkUser();
            // }
            Cookies.set('token', result[0].token, { expires: 7 })
            Cookies.set('fullName', result[0].fullName, { expires: 7 })
            openNotification(result[0].fullName);
        }
        else {
            openNotification();
        }
    };

    const onFinishFailed = errorInfo => {
        console.log('Failed:', errorInfo);
    };

    return (
        <>
            {contextHolder}
            <div onClick={showModal}>Đăng nhập</div>
            <Modal
                title="Đăng nhập tài khoản"
                open={isModalOpen}
                onCancel={onCancel}
                footer={null}
            >
                <Form
                    layout="horizontal"
                    initialValues={{ remember: true }}
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 16 }}
                    form={form}
                    labelAlign="left"
                >
                    <Form.Item
                        label="Tên tài khoản"
                        name="username"
                        rules={rules}
                        preserve={true}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="Mật khẩu"
                        name="password"
                        rules={rules}
                    >
                        <Input.Password />
                    </Form.Item>

                    <Form.Item name="remember" valuePropName="checked" label={null}>
                        <Checkbox>Ghi nhớ tài khoản</Checkbox>
                    </Form.Item>

                    <Form.Item label={null}>
                        <Button type="primary"  htmlType="submit">
                            Đăng nhập
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}

export default Login;