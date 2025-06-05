import { useEffect, useState } from 'react';
import { Button, Divider, Flex, notification, Popconfirm, Radio, Table } from 'antd';
import { delBook, getBook } from '../../../services/bookService';
import { getDetailCategory } from '../../../services/categoryService';
import { getDetailAuthor } from '../../../services/authorService';
import { useDispatch, useSelector } from 'react-redux';
import { deleteBook, setBook } from '../../../actions/book';
import UpdateBook from '../../../components/TopBook/UpdateBook';
import CreateBook from '../../../components/TopBook/CreateBook';

const columns = [
  {
    title: 'Ảnh',
    dataIndex: 'thumbnail',
    render: text => <a>{text}</a>,
  },
  {
    title: 'Tiêu đề',
    dataIndex: 'title',
  },
  {
    title: 'Mô tả',
    dataIndex: 'description',
  },
  {
    title: 'Giá',
    dataIndex: 'price',
  },
  {
    title: 'Giảm giá',
    dataIndex: 'discount',
  },
  {
    title: 'Số lượng',
    dataIndex: 'stock',
  },
  {
    title: 'Danh mục',
    dataIndex: 'category_name',
  },
  {
    title: 'Tác giả',
    dataIndex: 'author_name',
  },
  {
    title: 'Thời gian tạo',
    dataIndex: 'created_at',
  },
  {
    title: 'Hành động',
    width: 160,
    dataIndex: 'actions',
    fixed: 'right'
  },
];

const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
  },
};

function BookAdmin () {
  const [selectionType, setSelectionType] = useState('checkbox');
  const book = useSelector(state => state.bookReducer);
  const dispatch = useDispatch();
  const [api, contextHolder] = notification.useNotification();

  const handleDelete = async (id) => {
    await delBook(id);
    dispatch(deleteBook(id));
    api['success']({
      message: `Xóa thông tin thể loại thành công!`,
      duration: 1.5
    });
  }

  useEffect(() => {
    const fetchApi = async () => {
      const result = await getBook();

      const newData = await Promise.all(result.map(async (item) => {
        const date = new Date(item.created_at).toLocaleDateString();
        const category = await getDetailCategory(item.category_id);
        const author = await getDetailAuthor(item.author_id);

        item.category = category[0] || {name: "Không có"};
        item.author = author[0] || {name: "Không có"};
        
        return {
            key: item.id,
            id: item.id,
            thumbnail: item.thumbnail,
            title: item.title,
            description: item.description,
            price: item.price,
            discount: item.discount,
            stock: item.stock,
            category_id: item.category.id,
            author_id: item.author.id,
            category_name: item.category.name,
            author_name: item.author.name,
            created_at: date,
            actions: (
              <>
                <UpdateBook handleDelete={handleDelete} item={item}/>
                <Popconfirm
                  title="Xóa sách"
                  description="Bạn có chắc xóa sách này?"
                  okText="Đồng ý"
                  cancelText="Hủy bỏ"
                  onConfirm={() => handleDelete(item.id)}
                >
                  <Button type='primary' danger>Xóa</Button>
                </Popconfirm>
              </>
            )
        };
      }));

      dispatch(setBook(newData));      
    }
    fetchApi();
  }, [])

  return (
      <>
        {contextHolder}
        <h1>Sách</h1>
        <Flex justify='space-between' align='center'>
          <Radio.Group onChange={e => setSelectionType(e.target.value)} value={selectionType}>
            <Radio value="checkbox">Checkbox</Radio>
            <Radio value="radio">Radio</Radio>
          </Radio.Group>
          <CreateBook handleDelete={handleDelete}/>
        </Flex>
        <Divider />
        <Table
            rowSelection={Object.assign({ type: selectionType }, rowSelection)}
            columns={columns}
            dataSource={book}
            pagination={{pageSize: 5}}
            scroll={{x: 'max-content'}}
        />
      </>
  )
}

export default BookAdmin;