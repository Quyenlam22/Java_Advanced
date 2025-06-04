import { useEffect, useState } from 'react';
import { Button, Divider, Popconfirm, Radio, Table } from 'antd';
import { Link } from 'react-router-dom';
import { delAuthor, getAuthors } from '../../../services/authorService';
import { useDispatch, useSelector } from 'react-redux';
import { deleteAuthor, setAuthor } from '../../../actions/author';

const columns = [
  {
    title: 'Tên',
    dataIndex: 'name',
    render: text => <a>{text}</a>,
  },
  {
    title: 'Bio',
    dataIndex: 'bio',
  },
  {
    title: "Ảnh",
    dataIndex: "profile_image"
  },
  {
    title: 'Thời gian tạo',
    dataIndex: 'created_at',
  }, 
  {
    title: 'Hành động',
    width: 160,
    dataIndex: 'actions',
  },
];

const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
  },
};

function AuthorAdmin () {
  const [selectionType, setSelectionType] = useState('checkbox');
  const author = useSelector(state => state.authorReducer);
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchApi = async () => {
        const result = await getAuthors();
        dispatch(setAuthor(result));
    }
    fetchApi();
  }, [])

  const handleDelete = async (id) => {
    await delAuthor(id);
    dispatch(deleteAuthor(id));
  }

  const data = author.map(item => {
    const date = new Date(item.created_at).toLocaleDateString();

    return {
      key: item.id,
      name: item.name,
      bio: item.bio,
      profile_image: item.profile_image,
      created_at: date,
      actions: (
        <>
          <Link to={`edit/${item.id}`} className='mr-1'><Button type='primary'>Sửa</Button></Link>
          <Popconfirm
            title="Xóa tác giả"
            description="Bạn có chắc xóa tác giả này?"
            okText="Đồng ý"
            cancelText="Hủy bỏ"
            onConfirm={() => handleDelete(item.id)}
          >
            <Button type='primary' danger>Xóa</Button>
          </Popconfirm>
        </>
      )
    }
  })

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
          dataSource={data}
          pagination={{pageSize: 5}}
      />
    </>
  )
}

export default AuthorAdmin;