import { useEffect, useState } from 'react';
import { Button, Divider, Popconfirm, Radio, Table } from 'antd';
import { delBook, getBook } from '../../../services/bookService';
import { getDetailCategory } from '../../../services/categoryService';
import { getDetailAuthor } from '../../../services/authorService';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { deleteBook, setBook } from '../../../actions/book';

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
    dataIndex: 'category_id',
  },
  {
    title: 'Tác giả',
    dataIndex: 'author_id',
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

  const handleDelete = async (id) => {
    await delBook(id);
    dispatch(deleteBook(id));
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
            thumbnail: item.thumbnail,
            title: item.title,
            description: item.description,
            price: item.price,
            discount: item.discount,
            stock: item.stock,
            category_id: item.category.name,
            author_id: item.author.name,
            created_at: date,
            actions: (
              <>
                <Link to={`edit/${item.id}`} className='mr-1'><Button type='primary'>Sửa</Button></Link>
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

  console.log(book);
  

  return (
      <>
        <h1>Danh mục</h1>
        <Radio.Group onChange={e => setSelectionType(e.target.value)} value={selectionType}>
            <Radio value="checkbox">Checkbox</Radio>
            <Radio value="radio">radio</Radio>
        </Radio.Group>
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