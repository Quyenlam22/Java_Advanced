import { Button, Checkbox, Form, Input, Modal, notification } from "antd";
import { useState } from "react";
import { loginPost } from "../../services/authService";

function Login () {
    const [isModalOpen, setIsModalOpen] = useState(false);
    // const [form] = Form.useForm();
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
        // form.resetFields();
        setIsModalOpen(true);
    };
    const onCancel = () => {
        setIsModalOpen(false);
    }

    const onFinish = async (values) => {
        // const result = await loginPost(values);
        openNotification('Le Van A');
        // openNotification('');

        // if(!result){
        //     // openNotification("Hello");
        // }
    };
    const onFinishFailed = errorInfo => {
        console.log('Failed:', errorInfo);
    };

    return (
        <>
            {contextHolder}

            <p onClick={showModal}>Đăng nhập</p>
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
                    labelCol={{ span: 6 }}
                    wrapperCol={{ span: 18 }}
                >
                    <Form.Item
                        label="Tên tài khoản"
                        name="username"
                        rules={[{ required: true, message: 'Vui lòng nhập tài khoản!' }]}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="Mật khẩu"
                        name="password"
                        rules={[{ required: true, message: 'Vui lòng nhập mật khẩu!' }]}
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