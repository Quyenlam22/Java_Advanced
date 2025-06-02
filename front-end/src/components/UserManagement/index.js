import { useEffect, useState } from 'react';
import { Button, Divider, Radio, Table } from 'antd';
import { Link } from 'react-router-dom';
import { getUserByRole } from '../../services/userService';

const columns = [
  {
    title: 'Username',
    dataIndex: 'username',
    // render: text => <a>{text}</a>,
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
    dataIndex: 'actions'
  },
];

const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
  },
};

function UserManagement (props) {
    const { role } = props;
    console.log(role)
    const [selectionType, setSelectionType] = useState('checkbox');
    const [record, setRecord] = useState([]);

    useEffect(() => {
        const fetchApi = async () => {
            const result = await getUserByRole(role);
            setRecord(result);
        }
        fetchApi();
    }, [])

    const data = record.map(item => {
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
                  <Button type='primary' danger>Xóa</Button>
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
            />
        </>
    )
}

export default UserManagement;