import { Button, Form, Input, Modal, notification, Popconfirm, Select } from "antd";
import { useDispatch } from "react-redux";
import { useEffect, useState } from "react";
import { updateBook } from "../../services/bookService";
import { deleteBook, editBook } from "../../actions/book";
import { getAuthors, getDetailAuthor } from "../../services/authorService";
import { getCategories, getDetailCategory } from "../../services/categoryService";

const { Option } = Select;
const rules = [
  { 
    required: true, 
    message: 'Vui lòng không bỏ trống trường này!' 
  }
];

function UpdateBook (props) {
  const { item } = props;
  const dispatch = useDispatch();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form] = Form.useForm();
  const [api, contextHolder] = notification.useNotification();
  const [dataAuthors, setDataAuthors] = useState([]);
  const [dataCategories, setDataCategories] = useState([]);

  useEffect(() => {
    const fetchApi = async () => {
      const resultAuthors = await getAuthors();
      const resultCategories = await getCategories();

      setDataAuthors(resultAuthors);
      setDataCategories(resultCategories);
    }

    fetchApi();
  }, [])

  const showModal = () => {
    setIsModalOpen(true);
  };
  const onCancel = () => {
    setIsModalOpen(false);
    form.resetFields();
  };

  const handleEdit = (record) => {
    form.setFieldsValue(record);
    showModal();
  };

  const handleSubmit = async (values) => {
    const result = await updateBook(values, values.id);
    const category = await getDetailCategory(values.category_id);
    const author = await getDetailAuthor(values.author_id);

    const updatedRecord = {
      ...values,
      key: values.id,
      category: category[0],
      author: author[0],
      category_id: category[0]?.name || 'Không có',
      author_id: author[0]?.name || 'Không có',
      created_at: new Date(values.created_at).toLocaleDateString(),
      actions: (
        <>
          <UpdateBook
            item={{
              ...values,
              category: category[0],
              author: author[0]
            }}
          />
          <Popconfirm
            title="Xóa sách"
            description="Bạn có chắc xóa sách này?"
            okText="Đồng ý"
            cancelText="Hủy bỏ"
            onConfirm={() => dispatch(deleteBook(values.id))}
          >
            <Button type="primary" danger>Xóa</Button>
          </Popconfirm>
        </>
      )
    };
    
    if(result) {
      dispatch(editBook(updatedRecord));
      onCancel();
      api['success']({
        message: `Sửa thông tin sách thành công!`,
        duration: 1.5
      });
    }
  }

  return (
    <>
      {contextHolder}
      <Button className='mr-1' type="primary" onClick={() => handleEdit(item)}>Sửa</Button>
      <Modal
        title="Cập nhật thông tin sách"
        open={isModalOpen}
        footer={null}
        onCancel={onCancel}
      >
        <Form 
          form={form} 
          layout="vertical"
          // initialValues={item}
          onFinish={handleSubmit}
        >
          <Form.Item label="ID" name="id">
            <Input disabled />
          </Form.Item>
          <Form.Item
            label="Ảnh"
            name="thumbnail"
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Tiêu đề"
            name="title"
            rules={rules}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Mô tả"
            name="description"
          >
            <Input.TextArea rows={3} />
          </Form.Item>
          <Form.Item
            label="Giá"
            name="price"
            rules={rules}
          >
            <Input type="number" step="0.01" />
          </Form.Item>
          <Form.Item
            label="Giảm giá (%)"
            name="discount"
          >
            <Input type="number" step="0.01" min={0} max={1} />
          </Form.Item>
          <Form.Item
            label="Tồn kho"
            name="stock"
          >
            <Input type="number" />
          </Form.Item>
          <Form.Item
            label="Thể loại"
            name="category_id"
          >
            <Select placeholder="Chọn thể loại">
              {dataCategories.map((cat) => (
                <Option key={cat.id} value={cat.id}>
                  {cat.name}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            label="Tác giả"
            name="author_id"
          >
            <Select placeholder="Chọn tác giả">
              {dataAuthors.map((author) => (
                <Option key={author.id} value={author.id}>
                  {author.name}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item label="Ngày tạo" name="created_at">
            <Input disabled />
          </Form.Item>
          <Form.Item label="Ngày cập nhật" name="updated_at">
            <Input disabled />
          </Form.Item>
          <Form.Item >
            <Button htmlType='submit' type='primary'>Cập nhật</Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  )
}

export default UpdateBook ;