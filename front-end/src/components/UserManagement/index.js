import { useEffect, useState } from 'react';
import { Button, Divider, Popconfirm, Radio, Table } from 'antd';
import { Link } from 'react-router-dom';
import { delUser, getUserByRole } from '../../services/userService';
import { useDispatch, useSelector } from 'react-redux';
import { deleteUser, setUser } from '../../actions/user';

const columns = [
  {
    title: 'Username',
    dataIndex: 'username',
    // render: text => <a>{text}</a>,
    fixed: 'left'
  },
  {
    title: 'Email',
    dataIndex: 'email',
  },
  {
    title: "Họ và tên",
    dataIndex: "full_name"
  },
  {
    title: "Địa chỉ",
    dataIndex: "address"
  },
  {
    title: "Số điện thoại",
    dataIndex: "phone"
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

function UserManagement (props) {
  const { role } = props;
  const [selectionType, setSelectionType] = useState('checkbox');
  const user = useSelector(state => state.userReducer);
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchApi = async () => {
        const result = await getUserByRole(role);
        dispatch(setUser(result));
    }
    fetchApi();
  }, [])

  const handleDelete = async (id) => {
    await delUser(id);
    dispatch(deleteUser(id));
  }

  const data = user.map(item => {
    const date = new Date(item.created_at).toLocaleDateString();

    return {
      key: item.id,
      username: item.username,
      email: item.email,
      password: item.password,
      full_name: item.full_name,
      address: item.address,
      phone: item.phone,
      created_at: date,
      actions: (
        <>
          <Link to={`edit/${item.id}`} className='mr-1'><Button type='primary'>Sửa</Button></Link>
          <Popconfirm
              title="Xóa người dùng"
              description="Bạn có chắc xóa người dùng này?"
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
      <h1>Danh sách người dùng</h1>
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
          scroll={{x: 'max-content'}}
      />
    </>
  )
}

export default UserManagement;